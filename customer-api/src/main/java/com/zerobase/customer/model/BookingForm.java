package com.zerobase.customer.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class BookingForm {
	private LocalDateTime visitDate;
	private String visitDateStr;
	private String visitTimeStr;
	private Long customerId;
	private Long storeId;
}
