package com.zerobase.customer.service;


import com.zerobase.customer.dto.BookingDto;
import com.zerobase.customer.model.BookingForm;
import com.zerobase.domain.dto.common.StoreDto;

import java.util.List;

public interface StoreService{

	List<StoreDto> list();

	StoreDto detail(Long id);

	boolean createBooking(BookingForm parameter);

	List<BookingDto> bookingList(Long customerId);

}
