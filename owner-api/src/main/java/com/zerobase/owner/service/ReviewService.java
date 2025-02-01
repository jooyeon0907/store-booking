package com.zerobase.owner.service;

import com.zerobase.domain.dto.common.ReviewDto;

import java.util.List;

public interface ReviewService {
	List<ReviewDto> list(Long ownerId);

	ReviewDto getReview(Long id);
}
