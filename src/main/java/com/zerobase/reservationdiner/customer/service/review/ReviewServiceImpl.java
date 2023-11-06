package com.zerobase.reservationdiner.customer.service.review;

import com.zerobase.reservationdiner.customer.domain.Reservation;
import com.zerobase.reservationdiner.customer.domain.Review;
import com.zerobase.reservationdiner.customer.dto.review.ReviewRole;
import com.zerobase.reservationdiner.customer.dto.review.ReviewWrite;
import com.zerobase.reservationdiner.customer.exception.ReviewException;
import com.zerobase.reservationdiner.customer.repository.reservation.ReservationRepository;
import com.zerobase.reservationdiner.customer.repository.review.ReviewRepository;
import com.zerobase.reservationdiner.customer.type.ReviewErrorCode;
import com.zerobase.reservationdiner.member.domain.Member;
import com.zerobase.reservationdiner.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService{

    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    @Override
    public boolean checkReviewRole(ReviewRole reviewRole) {
        Member member = memberRepository.findByMemberId(reviewRole.getMemberId())
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.INVALID_MEMBERID));

        Reservation reservation = reservationRepository.findById(reviewRole.getReservationId())
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.INVALID_RESERVATION));

        return validationReview(member, reservation);

    }


    @Override
    public void insertReview(ReviewWrite write) {
        Member member = memberRepository.findByMemberId(write.getMemberId())
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.INVALID_MEMBERID));

        Reservation reservation = reservationRepository.findById(write.getReservationId())
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.INVALID_RESERVATION));

        validationReview(member, reservation);

        Review review = ReviewWrite.of(write);
        review.setMember(member);
        review.setReservation(reservation);

        reviewRepository.save(review);
    }

    //멤버아이디,예약내역 있는지 확인 후 -> 리뷰를 이미 작성하였는지 확인
    private boolean validationReview(Member member, Reservation reservation) {
        if(!member.getMemberId().equals(reservation.getMember().getMemberId())){
            throw new ReviewException(ReviewErrorCode.NOT_MATCH_MEMBERID);
        }else if(reservation.getCancel()||!reservation.getVisit()){
            throw new ReviewException(ReviewErrorCode.NOT_VISTIT_STORE);
        }

        Optional<Review> review = reviewRepository.findByMemberIdAndReservationId(member.getId(), reservation.getId());
        if(review.isPresent()){
           return false;
        }

        return true;
    }
}
