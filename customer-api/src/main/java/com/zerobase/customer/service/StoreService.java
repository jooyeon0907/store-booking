package com.zerobase.customer.service;


import com.zerobase.domain.dto.common.BookingDto;
import com.zerobase.customer.model.BookingForm;
import com.zerobase.domain.dto.common.StoreDto;
import com.zerobase.domain.model.common.StoreParam;

import java.util.List;

public interface StoreService{

	List<StoreDto> list(StoreParam parameter);

	StoreDto detail(Long id);

	boolean createBooking(BookingForm parameter);

	List<BookingDto> bookingList(Long customerId);

}
