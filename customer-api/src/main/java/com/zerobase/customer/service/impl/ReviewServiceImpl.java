package com.zerobase.customer.service.impl;

import com.zerobase.customer.repository.BookingRepository;
import com.zerobase.customer.repository.CustomerRepository;
import com.zerobase.customer.repository.ReviewRepository;
import com.zerobase.customer.repository.StoreRepository;
import com.zerobase.customer.service.ReviewService;
import com.zerobase.domain.dto.common.BookingDto;
import com.zerobase.domain.dto.common.ReviewDto;
import com.zerobase.domain.entity.common.Booking;
import com.zerobase.domain.entity.common.Review;
import com.zerobase.domain.model.common.ReviewParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final CustomerRepository customerRepository;
	private final StoreRepository storeRepository;
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
}
