package com.zerobase.kiosk.service;

import com.zerobase.domain.dto.common.BookingDto;
import com.zerobase.domain.dto.common.StoreDto;
import com.zerobase.kiosk.model.BookingForm;
import com.zerobase.kiosk.model.SignInForm;

public interface KioskService {
	Long login(SignInForm parameter);

	StoreDto getStore(Long ownerId);

	Long getCustomerId(String phone);

	BookingDto getBooking(Long customerId, BookingForm parameter);
}
