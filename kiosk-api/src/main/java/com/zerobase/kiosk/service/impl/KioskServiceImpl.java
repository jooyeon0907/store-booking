package com.zerobase.kiosk.service.impl;

import com.zerobase.domain.dto.common.BookingDto;
import com.zerobase.domain.dto.common.StoreDto;
import com.zerobase.domain.entity.common.Booking;
import com.zerobase.domain.entity.common.BookingStatus;
import com.zerobase.domain.entity.common.Store;
import com.zerobase.domain.entity.customer.Customer;
import com.zerobase.domain.entity.owner.Owner;
import com.zerobase.kiosk.model.BookingForm;
import com.zerobase.kiosk.model.SignInForm;
import com.zerobase.kiosk.repository.BookingRepository;
import com.zerobase.kiosk.repository.CustomerRepository;
import com.zerobase.kiosk.repository.OwnerRepository;
import com.zerobase.kiosk.repository.StoreRepository;
import com.zerobase.kiosk.service.KioskService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KioskServiceImpl implements KioskService {

	private final OwnerRepository ownerRepository;
	private final CustomerRepository customerRepository;
	private final StoreRepository storeRepository;
	private final BookingRepository bookingRepository;


	@Override
	public Long getOwnerId(SignInForm parameter) {

		Optional<Owner> optionalOwner = ownerRepository.findByName(parameter.getName());
		if (!optionalOwner.isPresent()) {
			throw new RuntimeException("회원 정보가 존재하지 않습니다.");
		}

		 if (!BCrypt.checkpw(parameter.getPassword(), optionalOwner.get().getPassword())) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다. 다시 시도해주세요.");
        }

		return optionalOwner.get().getId();
	}

	@Override
	public StoreDto getStore(Long ownerId) {
		//
		Store store = storeRepository.findByOwnerId(ownerId)
				.orElseThrow(() -> new RuntimeException("등록된 매장이 없습니다. \n 관리자 홈페이지에서 매장을 등록해 주세요."));
		return StoreDto.of(store);
	}

	@Override
	public Long getCustomerId(String phone) {
		Customer customer = customerRepository.findByPhone(phone)
				.orElseThrow(() -> new RuntimeException("회원 정보가 존재하지 않습니다."));
		return customer.getId();
	}

	/**
	 * 예약 조회
	 * 	- 점주가 예약 승인한 금일 예약건 조회
	 */
	@Override
	public BookingDto getBooking(Long customerId, BookingForm parameter) {
		List<Booking> bookings =
				bookingRepository.findByCustomerIdAndStoreIdAndBookingStatus(customerId, parameter.getStoreId(), BookingStatus.APPROVED)
								.stream()
								.filter(booking -> booking.getVisitDate().toLocalDate().isEqual(LocalDate.now()))
								.collect(Collectors.toList());

		if (bookings.isEmpty()) {
			throw new RuntimeException("금일 방문 예약이 없습니다. 예약 시간 및 상태를 확인해 주세요.");
		}

		return BookingDto.of(bookings.get(0));
	}

	/**
	 * 방문 완료 업데이트
	 * 	- 방문 가능한 시간은 예약 시간 1시간 전부터 ~ 10분 전까지
	 * 	- 그 외에 시간에 방문 요청 시, 실패 처리됨
	 */
	@Override
	public void visitStatusUpdate(Long id) {

		Booking booking = bookingRepository.findById(id).get();

		LocalDateTime now = LocalDateTime.now();

		LocalDateTime tenMinutesBeforeVisit = booking.getVisitDate().minus(10, ChronoUnit.MINUTES);  // visitDate에서 10분 전
		if (!now.isBefore(tenMinutesBeforeVisit)){ // 현재 시간이 visitDate 10분 전보다 이전인지 확인
			throw  new RuntimeException("방문 가능한 시간은 예약 시간 10분 전까지입니다. 지나친 시간이므로 방문을 처리할 수 없습니다.");
		}

		LocalDateTime hourBeforeVisit = booking.getVisitDate().minus(1, ChronoUnit.HOURS);
		if (now.isBefore(hourBeforeVisit)) {
			throw  new RuntimeException("방문 가능한 시간은 예약 시간 1시간 전부터입니다. 나중에 다시 방문 요청해 주세요.");
		}

		booking.setVisitStatus(true);
		bookingRepository.save(booking);

	}
}
