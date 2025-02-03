package com.zerobase.customer.service.impl;

import com.zerobase.customer.mapper.ReviewMapper;
import com.zerobase.customer.repository.BookingRepository;
import com.zerobase.customer.repository.ReviewRepository;
import com.zerobase.customer.repository.StoreRepository;
import com.zerobase.customer.service.ReviewService;
import com.zerobase.domain.dto.common.ReviewDto;
import com.zerobase.domain.entity.common.Booking;
import com.zerobase.domain.entity.common.Review;
import com.zerobase.domain.entity.common.Store;
import com.zerobase.domain.model.common.ReviewParam;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	@PersistenceContext
    private EntityManager entityManager;


	private final BookingRepository bookingRepository;
	private final ReviewRepository reviewRepository;
	private final StoreRepository storeRepository;
	private final ReviewMapper reviewMapper;

	@Override
	public ReviewDto write(ReviewParam parameter) {
		Booking booking = bookingRepository.findById(parameter.getBookingId())
				.orElseThrow(() -> new RuntimeException("예약건이 없습니다."));


		Review review = Review.builder()
				.content(parameter.getContent())
				.score(parameter.getScore())
				.booking(booking)
				.build();
		reviewRepository.save(review);

		// 리뷰 작성 후, 해당 매장의 점수 업데이트
		updateAverageScore(booking.getStore());

		return ReviewDto.of(review);
	}

	private void updateAverageScore(Store store) {
		// 해당 매장의 모든 리뷰 가져오기
		List<Review> reviews = reviewMapper.selectReviewsByStoreId(store.getId());

		// 별점 평균 계산
		double averageScore = reviews.stream()
				.mapToInt(Review::getScore)
				.average()
				.orElse(0.0);

		store.setAverageScore(averageScore);
		storeRepository.save(store);
	}

	@Override
	public List<ReviewDto> list(Long customerId) {
		List<Review> reviews = reviewRepository.findByCustomerId(customerId);
		return ReviewDto.of(reviews);
	}

	@Override
	public ReviewDto getReview(Long id) {
		Review review = reviewRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("등록된 리뷰가 없습니다."));
		return ReviewDto.of(review);
	}

	@Override
	public boolean update(ReviewParam parameter) {
		Optional<Review> optionalReview = reviewRepository.findById(parameter.getId());
		if (optionalReview.isPresent()) {
			Review review = optionalReview.get();
			review.setScore(parameter.getScore());
			review.setContent(parameter.getContent());
			reviewRepository.save(review);

			// 리뷰 작성 후, 해당 매장의 점수 업데이트
        	updateAverageScore(review.getBooking().getStore());
			return true;
		}

		return false;
	}

	@Override
	public boolean del(Long id) {
		Review review = reviewRepository.findById(id).orElseThrow();
		review.setBooking(null);
		reviewRepository.flush();
		entityManager.clear();
		reviewRepository.delete(review);

		return true;
	}

}
