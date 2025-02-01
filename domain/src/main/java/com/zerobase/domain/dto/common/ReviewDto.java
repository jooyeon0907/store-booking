package com.zerobase.domain.dto.common;

import com.zerobase.domain.entity.common.Booking;
import com.zerobase.domain.entity.common.Review;
import com.zerobase.domain.entity.common.Store;
import com.zerobase.domain.entity.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

	private Long id;
	private String content;
	private int score;
	private LocalDateTime writeDate;
	private Booking booking;

	public static ReviewDto of(Review review) {
		return ReviewDto.builder()
				.id(review.getId())
				.content(review.getContent())
				.score(review.getScore())
				.writeDate(review.getWriteDate())
				.booking(review.getBooking())
				.build();
	}

	public static List<ReviewDto> of(List<Review> reviews) {
		if (reviews != null) {
			List<ReviewDto> reviewList = new ArrayList<>();
			for(Review r : reviews) {
				reviewList.add(of(r));
			}
			return reviewList;
		}

		return null;
	}
}
