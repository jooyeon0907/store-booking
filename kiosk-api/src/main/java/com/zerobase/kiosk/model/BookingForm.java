package com.zerobase.kiosk.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookingForm {
	private String phone;
	private Long storeId;
}
