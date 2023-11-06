package com.zerobase.reservationdiner.owner.service;

import com.zerobase.reservationdiner.customer.domain.Reservation;
import com.zerobase.reservationdiner.customer.exception.store.StoreException;
import com.zerobase.reservationdiner.customer.repository.reservation.ReservationRepository;
import com.zerobase.reservationdiner.member.domain.Member;
import com.zerobase.reservationdiner.member.repository.MemberRepository;
import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import com.zerobase.reservationdiner.owner.domain.TimeSlot;
import com.zerobase.reservationdiner.owner.dto.*;
import com.zerobase.reservationdiner.owner.exception.OwnerException;
import com.zerobase.reservationdiner.owner.exception.ReservePermitException;
import com.zerobase.reservationdiner.owner.repository.owner.OwnerRepository;
import com.zerobase.reservationdiner.owner.repository.timeslot.TimeSlotRepository;
import com.zerobase.reservationdiner.owner.type.ReservePermitErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.zerobase.reservationdiner.customer.type.StoreErrorCode.*;
import static com.zerobase.reservationdiner.owner.type.OwnerErrorCode.*;
import static com.zerobase.reservationdiner.owner.type.OwnerErrorCode.ALREADY_OPEN;

@Service
@Slf4j
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService{

    private final OwnerRepository ownerRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public StoreRegister registerStore(StoreInput input) {
        System.out.println(input.getOwnerId());
        Member owner = memberRepository.findByMemberId(input.getOwnerId()).orElseThrow(() -> new OwnerException(NOEXIST_OWNER));

        boolean exist = ownerRepository.existsByStoreNameAndAddressZipcode(input.getStoreName(), input.getAddress().getZipcode());
        System.out.println(owner);
        if(exist) {
            throw new OwnerException(ALREADY_EXIST);
        }
        OwnerStore newStore = StoreInput.of(input);
        newStore.setOwner(owner);

        OwnerStore ownerStore = ownerRepository.save(newStore);
        StoreRegister storeRegister = StoreRegister.of(ownerStore);

        return StoreRegister.of(ownerStore);
    }

    @Override
    @Transactional
    public void openAndTimeSlotAdd(StoreOpen open) {
        Member owner = memberRepository.findByMemberId(open.getOwnerId()).orElseThrow(() -> new StoreException(NOTFOUND_STORE));
        OwnerStore store = ownerRepository.findByIdAndOwnerId(open.getStoreId(),owner.getId()).orElseThrow(() -> new StoreException(NOTFOUND_STORE));
        if(store.getOpenStatus()==open.getOpen()) {
            throw new OwnerException(ALREADY_OPEN);
        }

        store.setOpenStatus(open.getOpen());
        ownerRepository.save(store);

        //첫번째 오픈일때 한달치 예약시간표 db에 넣어주기
        List<TimeSlot> timeslots = timeSlotRepository.findByStoreId(store.getId());
        if(timeslots.isEmpty()){
            insertTimeSlot(store);
        }
    }

    private void insertTimeSlot(OwnerStore store) {
        LocalTime openTime = store.getOpenTime();
        LocalTime closeTime = store.getCloseTime();

        LocalDateTime currentDateTime = LocalDateTime.now().plusDays(1);
        LocalDateTime oneMonthLater = currentDateTime.plusMonths(1);

        while (currentDateTime.isBefore(oneMonthLater)) {
            LocalDateTime currentSlot = currentDateTime.with(openTime);
            while (currentSlot.isBefore(currentDateTime.with(closeTime))) {
                timeSlotRepository.save(TimeSlot.builder()
                                .store(store)
                                .time(currentDateTime)
                        .build());
                currentSlot = currentSlot.plusHours(1);
            }

            currentDateTime = currentDateTime.plusDays(1);
        }
    }

    @Override
    public boolean permitReservation(ReserveInfo.Request request) {
        Reservation reservation = reservationRepository.findById(request.getReservationId()).
                orElseThrow(() -> new ReservePermitException(ReservePermitErrorCode.NOT_EXIST_RESERVE));

        TimeSlot timeSlot = timeSlotRepository.findById(reservation.getTimeSlot().getId())
                .orElseThrow(() -> new ReservePermitException(ReservePermitErrorCode.NOT_EXIST_TIME));

        validationPermitReservation(request.getPermit(),timeSlot, reservation);

        reservation.setOwnercheck(request.getPermit());
        if(request.getPermit()) {
            timeSlot.setReserve(true);
            timeSlotRepository.save(timeSlot);
        }
        reservationRepository.save(reservation);

        return reservation.getOwnercheck();
    }

    private static void validationPermitReservation(boolean reservationPermit,TimeSlot timeSlot, Reservation reservation) {
        if(reservationPermit&&timeSlot.getReserve()){
            throw new ReservePermitException(ReservePermitErrorCode.ALREADY_PERMIT_OTHER_CUSTOMER);
        }

        if(reservation.getOwnercheck()) {
            throw new ReservePermitException(ReservePermitErrorCode.ALREADY_PERMIT);
        }else if(reservation.getCancel()){
            throw new ReservePermitException(ReservePermitErrorCode.ALREADY_CANCEL);
        }
    }

    @Override
    public List<ReserveList.Response> getAllReservation(ReserveList.Request request) {
        Member member = memberRepository.findByMemberId(request.getOwnerId()).orElseThrow(() -> new OwnerException(NOEXIST_OWNER));
        OwnerStore store = ownerRepository.findByOwnerIdAndStoreName(member.getId(), request.getStoreName())
                .orElseThrow(() -> new OwnerException(NOEXIST_OWNER));

        List<TimeSlot> reserveTimeSlots = timeSlotRepository.findByStoreIdAndReserveTrueAndTimeBetween(store.getId(),request.getStartDate(),request.getEndDate());
        return reserveTimeSlots.stream().map(slot -> ReserveList.Response
                        .builder()
                        .customerCnt(slot.getReservation().getCustomerCnt())
                        .storeName(store.getStoreName())
                        .customerName(slot.getReservation().getMember().getMemberName())
                        .phoneNumber(slot.getReservation().getPhoneNumber())
                        .reserveDate(slot.getReservation().getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
