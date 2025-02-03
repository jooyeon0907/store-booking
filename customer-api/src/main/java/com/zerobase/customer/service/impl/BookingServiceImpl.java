package com.zerobase.customer.service.impl;

import com.zerobase.customer.model.BookingForm;
import com.zerobase.customer.repository.BookingRepository;
import com.zerobase.customer.repository.CustomerRepository;
import com.zerobase.customer.repository.StoreRepository;
import com.zerobase.customer.service.BookingService;
import com.zerobase.domain.dto.common.BookingDto;
import com.zerobase.domain.entity.common.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

	private final CustomerRepository customerRepository;
	private final StoreRepository storeRepository;
	private final BookingRepository bookingRepository;


	@Override
	public boolean create(BookingForm parameter) {
			//  같은 매장에 1일 1건 예약 정책으로, 같은 날짜에 예약을 여러건 할 수 없음

			String combinedDateTime = parameter.getVisitDateStr() + "T" + parameter.getVisitTimeStr();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
			LocalDateTime visitDateTime = LocalDateTime.parse(combinedDateTime, formatter);
			LocalDate visitDateOnly = visitDateTime.toLocalDate();

			// 같은 고객이 이미 같은 날짜에 예약했는지 확인 (시간은 무시하고 날짜만 비교)
			bookingRepository.findByStoreIdAndCustomerIdAndVisitDate(
				parameter.getStoreId(),
				parameter.getCustomerId(),
				visitDateOnly
			).ifPresent(b -> { throw new RuntimeException("같은 매장에서 같은 고객은 1일 1건만 예약 가능합니다."); });

			// 다른 고객이 해당 시간에 예약했는지 확인
			bookingRepository.findByStoreIdAndVisitDate(parameter.getStoreId(), visitDateTime)
					.ifPresent(b -> {throw new RuntimeException("해당 시간대는 이미 예약되었습니다.");});

		Booking booking = Booking.builder()
				.visitDate(visitDateTime)
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
