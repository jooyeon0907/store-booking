package com.zerobase.domain.model.common;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ReviewParam {
	private Long id;
	private Long bookingId;
	private String storeName;
	private int score;
	private String content;
}
