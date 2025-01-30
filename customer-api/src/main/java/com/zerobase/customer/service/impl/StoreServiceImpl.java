package com.zerobase.customer.service.impl;

import com.zerobase.customer.dto.BookingDto;
import com.zerobase.customer.model.BookingForm;
import com.zerobase.customer.repository.BookingRepository;
import com.zerobase.customer.repository.CustomerRepository;
import com.zerobase.customer.repository.StoreRepository;
import com.zerobase.customer.service.StoreService;
import com.zerobase.domain.dto.common.StoreDto;
import com.zerobase.domain.entity.common.Booking;
import com.zerobase.domain.entity.common.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

	private final CustomerRepository customerRepository;
	private final StoreRepository storeRepository;
	private final BookingRepository bookingRepository;

	private Sort getSortBySortValueDesc() {
		return Sort.by(Sort.Direction.DESC, "sortValue");
	}

	@Override
	public List<StoreDto> list() {
		List<Store> stores = storeRepository.findAll();
		return StoreDto.of(stores);
	}

	@Override
	public StoreDto detail(Long id) {
		return StoreDto.of(storeRepository.findById(id).get());
	}

	@Override
	public boolean createBooking(BookingForm parameter) {
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
	public List<BookingDto> bookingList(Long customerId) {
		List<Booking> bookings = bookingRepository.findByCustomerId(customerId);
		return BookingDto.of(bookings);
	}

}
