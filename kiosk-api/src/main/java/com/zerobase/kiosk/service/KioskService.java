package com.zerobase.kiosk.service;

import com.zerobase.domain.dto.common.BookingDto;
import com.zerobase.domain.dto.common.StoreDto;
import com.zerobase.kiosk.model.BookingForm;
import com.zerobase.kiosk.model.SignInForm;

public interface KioskService {
	StoreDto getStore(Long ownerId);

	Long getCustomerId(String phone);

	BookingDto getBooking(Long customerId, BookingForm parameter);

	void visitStatusUpdate(Long id);

	Long getOwnerId(SignInForm parameter);
}
