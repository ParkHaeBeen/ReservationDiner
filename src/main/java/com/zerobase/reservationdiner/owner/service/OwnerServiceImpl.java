package com.zerobase.reservationdiner.owner.service;

import com.zerobase.reservationdiner.customer.domain.Reservation;
import com.zerobase.reservationdiner.customer.exception.store.StoreException;
import com.zerobase.reservationdiner.customer.repository.reservation.ReservationRepository;
import com.zerobase.reservationdiner.customer.type.StoreErrorCode;
import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import com.zerobase.reservationdiner.owner.domain.TimeSlot;
import com.zerobase.reservationdiner.owner.dto.ReserveInfo;
import com.zerobase.reservationdiner.owner.dto.StoreInput;
import com.zerobase.reservationdiner.owner.dto.StoreOpen;
import com.zerobase.reservationdiner.owner.exception.OwnerException;
import com.zerobase.reservationdiner.owner.exception.ReservePermitException;
import com.zerobase.reservationdiner.owner.repository.OwnerRepository;
import com.zerobase.reservationdiner.owner.repository.TimeSlotRepository;
import com.zerobase.reservationdiner.owner.type.OwnerErrorCode;
import com.zerobase.reservationdiner.owner.type.ReservePermitErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.spi.ToolProvider;

import static com.zerobase.reservationdiner.customer.type.StoreErrorCode.*;
import static com.zerobase.reservationdiner.owner.type.OwnerErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService{

    private final OwnerRepository ownerRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public void registerStore(StoreInput input) {
        boolean exist = ownerRepository.existsByStoreNameAndAddressZipcode(input.getStoreName(), input.getAddress().getZipcode());

        if(exist) {
            throw new OwnerException(ALREADY_EXIST);
        }
        OwnerStore newStore = StoreInput.of(input);
        ownerRepository.save(newStore);

    }

    @Override
    @Transactional
    public void openAndTimeSlotAdd(StoreOpen open) {
        OwnerStore store = ownerRepository.findByIdAndOwnerId(open.getStoreId(),open.getOwnerId()).orElseThrow(() -> new StoreException(NOTFOUND_STORE));
        if(store.isOpenStatus()==open.getOpen()) {
            throw new StoreException(StoreErrorCode.ALREADY_OPEN);
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
    public void permitReservation(ReserveInfo.Request request) {
        Reservation reservation = reservationRepository.findById(request.getReservationId()).
                orElseThrow(() -> new ReservePermitException(ReservePermitErrorCode.NOT_EXIST_RESERVE));

        if(reservation.getOwnercheck()) {
            throw new ReservePermitException(ReservePermitErrorCode.ALREADY_PERMIT);
        }else if(reservation.getCancel()){
            throw new ReservePermitException(ReservePermitErrorCode.ALREADY_CANCEL_BYCUSTOMER);
        }

        reservation.setOwnercheck(true);
        reservationRepository.save(reservation);
    }
}
