package com.zerobase.owner.service.impl;

import com.zerobase.domain.dto.common.ReviewDto;
import com.zerobase.domain.entity.common.Review;
import com.zerobase.domain.entity.common.Store;
import com.zerobase.owner.repository.ReviewRepository;
import com.zerobase.owner.repository.StoreRepository;
import com.zerobase.owner.service.ReviewService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	@PersistenceContext
    private EntityManager entityManager;


	private final StoreRepository storeRepository;
	private final ReviewRepository reviewRepository;

	/**
	 * 로그인 된 점주 매장에 작성된 리뷰 목록 조회
	 */
	@Override
	public List<ReviewDto> list(Long ownerId) {
		Optional<Store> optionalStore = storeRepository.findByOwnerId(ownerId);
		if (optionalStore.isEmpty()) {
			return new ArrayList<>();
		}

		List<Review> reviews = reviewRepository.findByStoreId(optionalStore.get().getId());
		return ReviewDto.of(reviews);
	}

	@Override
	public ReviewDto getReview(Long id) {
		Review review = reviewRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("등록된 리뷰가 없습니다."));
		return ReviewDto.of(review);
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
