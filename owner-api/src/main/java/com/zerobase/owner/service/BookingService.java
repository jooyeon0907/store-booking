package com.zerobase.owner.service;

import com.zerobase.domain.dto.common.BookingDto;

import java.util.List;

public interface BookingService {
	List<BookingDto> list(Long ownerId);
}
