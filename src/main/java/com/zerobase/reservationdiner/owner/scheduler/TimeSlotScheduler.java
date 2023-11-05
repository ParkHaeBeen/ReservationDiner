package com.zerobase.reservationdiner.owner.scheduler;

import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import com.zerobase.reservationdiner.owner.domain.TimeSlot;
import com.zerobase.reservationdiner.owner.repository.owner.OwnerRepository;
import com.zerobase.reservationdiner.owner.repository.timeslot.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TimeSlotScheduler {

    private final TimeSlotRepository timeSlotRepository;
    private final OwnerRepository ownerRepository;

    //한달은 미리 채워두고 다음달 하루 예약일정 저장
    @Scheduled(cron = "0 0 0 * * ?")
    public void timeSlotScheduling(){

        List<OwnerStore> allStores = ownerRepository.findAll();

        for (OwnerStore store : allStores) {
            if(store.getOpenStatus()) {
                log.info("storeName = {}", store.getStoreName());
                LocalTime openTime = store.getOpenTime();
                LocalTime closeTime = store.getCloseTime();

                makeTimeSlot(openTime, closeTime, store);
            }
        }


    }

    private void makeTimeSlot(LocalTime openTime, LocalTime closeTime,OwnerStore store) {
        LocalDateTime oneMonthLater=LocalDateTime.now().plusMonths(1);

        LocalDateTime startSlot = oneMonthLater.with(openTime);
        while (startSlot.isBefore(oneMonthLater.with(closeTime))){
            timeSlotRepository.save(TimeSlot.builder()
                            .store(store)
                            .time(startSlot)
                    .build());

            startSlot=startSlot.plusHours(1);
        }
    }
}
