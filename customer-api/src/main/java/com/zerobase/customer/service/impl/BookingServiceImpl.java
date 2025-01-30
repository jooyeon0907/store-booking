package com.zerobase.customer.service.impl;

import com.zerobase.domain.dto.common.BookingDto;
import com.zerobase.customer.model.BookingForm;
import com.zerobase.customer.repository.BookingRepository;
import com.zerobase.customer.repository.CustomerRepository;
import com.zerobase.customer.repository.StoreRepository;
import com.zerobase.customer.service.BookingService;
import com.zerobase.domain.entity.common.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

	private final CustomerRepository customerRepository;
	private final StoreRepository storeRepository;
	private final BookingRepository bookingRepository;


	@Override
	public boolean create(BookingForm parameter) {
		bookingRepository.findByStoreIdAndVisitDate(parameter.getStoreId(), parameter.getVisitDate())
				.ifPresent(store -> {
					throw new RuntimeException("해당 시간은 이미 예약되어 있습니다.");
				});


		Booking booking = Booking.builder()
				.visitDate(parameter.getVisitDate())
				.customer(customerRepository.findById(parameter.getCustomerId()).get())
				.store(storeRepository.findById(parameter.getStoreId()).get())
				.build();
		bookingRepository.save(booking);

		return true;
	}

	@Override
	public List<BookingDto> list(Long customerId) {
		List<Booking> bookings = bookingRepository.findByCustomerId(customerId);
		return BookingDto.of(bookings);
	}

}
