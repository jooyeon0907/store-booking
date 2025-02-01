package com.zerobase.customer.service.impl;

import com.zerobase.customer.repository.BookingRepository;
import com.zerobase.customer.repository.ReviewRepository;
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

		return ReviewDto.of(review);
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
