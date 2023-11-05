package com.zerobase.reservationdiner.customer.dto.reservation;

import com.zerobase.reservationdiner.customer.domain.Reservation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

public class ReservationInfo {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{

        @NotNull
        private LocalDateTime reservationDate;

        @Pattern(regexp = "^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$")
        @NotBlank
        private String phoneNumber;

        @NotBlank
        private String memberId;

        @NotNull
        private Integer customerCnt;

        @NotNull
        private Long timeslotId;

        public static Reservation of(ReservationInfo.Request request){
            return Reservation.builder()
                    .phoneNumber(request.getPhoneNumber())
                    .customerCnt(request.getCustomerCnt())
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{

        private Integer customerCnt;
        private LocalDateTime reservationDate;

        public static ReservationInfo.Response of(Reservation reservation){
            return Response.builder()
                    .customerCnt(reservation.getCustomerCnt())
                    .reservationDate(reservation.getTimeSlot().getTime())
                    .build();
        }
    }
}
