package com.zerobase.customer.service;


import com.zerobase.customer.dto.BookingDto;
import com.zerobase.customer.model.BookingForm;

import java.util.List;

public interface BookingService {

	boolean create(BookingForm parameter);

	List<BookingDto> list(Long customerId);

}
