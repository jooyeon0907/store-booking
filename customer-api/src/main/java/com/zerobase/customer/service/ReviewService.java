package com.zerobase.customer.service;


import com.zerobase.domain.dto.common.ReviewDto;
import com.zerobase.domain.model.common.ReviewParam;

import java.util.List;

public interface ReviewService {

	ReviewDto write(ReviewParam parameter);

	List<ReviewDto> list(Long customerId);

	ReviewDto getReview(Long id);

	boolean update(ReviewParam parameter);

	boolean del(Long id);
}
