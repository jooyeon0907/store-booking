package com.zerobase.owner.service;

import com.zerobase.domain.dto.common.BookingDto;
import com.zerobase.owner.model.BookingForm;

import java.util.List;

public interface BookingService {
	List<BookingDto> list(Long ownerId);

	boolean updateBookingStatus(BookingForm parameter);
}
