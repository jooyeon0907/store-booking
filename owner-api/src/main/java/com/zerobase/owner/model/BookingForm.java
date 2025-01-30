package com.zerobase.owner.model;

import com.zerobase.domain.entity.common.BookingStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookingForm {
	private Long id;
    private BookingStatus bookingStatus;
}