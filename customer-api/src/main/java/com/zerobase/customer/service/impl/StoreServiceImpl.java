package com.zerobase.customer.service.impl;

import com.zerobase.customer.mapper.ReviewMapper;
import com.zerobase.customer.mapper.StoreMapper;
import com.zerobase.customer.repository.ReviewRepository;
import com.zerobase.domain.dto.common.BookingDto;
import com.zerobase.customer.model.BookingForm;
import com.zerobase.customer.repository.BookingRepository;
import com.zerobase.customer.repository.CustomerRepository;
import com.zerobase.customer.repository.StoreRepository;
import com.zerobase.customer.service.StoreService;
import com.zerobase.domain.dto.common.StoreDto;
import com.zerobase.domain.entity.common.Booking;
import com.zerobase.domain.entity.common.Review;
import com.zerobase.domain.entity.common.Store;
import com.zerobase.domain.model.common.StoreParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

	private final CustomerRepository customerRepository;
	private final StoreRepository storeRepository;
	private final BookingRepository bookingRepository;
	private final ReviewRepository reviewRepository;
	private final StoreMapper storeMapper;
	private final ReviewMapper reviewMapper;

	private Sort getSortBySortValueDesc() {
		return Sort.by(Sort.Direction.DESC, "sortValue");
	}

	@Override
	public List<StoreDto> list(StoreParam parameter) {
		long totalCount = storeMapper.selectListCount(parameter);
		List<StoreDto> list = storeMapper.selectList(parameter);


		if (!CollectionUtils.isEmpty(list)) {
			for(StoreDto store: list) {
				store.setTotalCount(totalCount);
			}
		}

		return list;
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
