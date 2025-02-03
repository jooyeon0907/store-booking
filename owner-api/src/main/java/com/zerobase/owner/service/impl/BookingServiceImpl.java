package com.zerobase.owner.service.impl;

import com.zerobase.domain.dto.common.BookingDto;
import com.zerobase.domain.entity.common.Booking;
import com.zerobase.domain.entity.common.Store;
import com.zerobase.owner.model.BookingForm;
import com.zerobase.owner.repository.BookingRepository;
import com.zerobase.owner.repository.StoreRepository;
import com.zerobase.owner.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

	private final StoreRepository storeRepository;
	private final BookingRepository bookingRepository;

	/**
	 * 로그인 된 점주 매장에 예약된 목록 조회
	 */
	@Override
	public List<BookingDto> list(Long ownerId) {
		Optional<Store> optionalStore = storeRepository.findByOwnerId(ownerId);
		if (optionalStore.isEmpty()) {
			return new ArrayList<>();
		}

		List<Booking> bookings = bookingRepository.findByStoreId(optionalStore.get().getId());
		return BookingDto.of(bookings);
	}

	@Override
	public boolean updateBookingStatus(BookingForm parameter) {
		Booking booking = bookingRepository.findById(parameter.getId()).get();

		booking.setBookingStatus(parameter.getBookingStatus());
		bookingRepository.save(booking);

		return true;
	}

}
