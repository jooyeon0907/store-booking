package com.zerobase.kiosk.service.impl;

import com.zerobase.domain.dto.common.BookingDto;
import com.zerobase.domain.dto.common.StoreDto;
import com.zerobase.domain.entity.common.Booking;
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
	public Long login(SignInForm parameter) {

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

	@Override
	public BookingDto getBooking(Long customerId, BookingForm parameter) {
		/*
			예약 정보 조회 화면에서 고객 폰 번호 입력
			폰 번호로 customer 에서 고객 정보 찾기
			고객 id 로 booking 조회

			조회된 booking 이 있으면 booking 객체 반환 -> 예약 내역 화면으로 이동
			조회된 예약은 있지만 예약 시간 10분 전 보다 지낫다면 -> '예약 시간 10분 전이 지나, 예약이 취소되었습니다.'
			조회된 booking 이 없으면 false- > '조회된 예약 내역이 없습니다'

		 */

		List<Booking> bookings = bookingRepository.findByCustomerIdAndStoreId(customerId, parameter.getStoreId())
        .stream()
        .filter(booking -> booking.getVisitDate().toLocalDate().isEqual(LocalDate.now()))
        .collect(Collectors.toList());

		if (bookings.isEmpty()) {
			throw new RuntimeException("금일 방문 예약이 없습니다.");
		}

//		Booking booking = bookings.get(0);
//		LocalDateTime now = LocalDateTime.now();
//		LocalDateTime tenMinutesBeforeVisit = booking.getVisitDate().minus(10, ChronoUnit.MINUTES);  // visitDate에서 10분 전
//		boolean isVisitBeforeTenMinutes = now.isBefore(tenMinutesBeforeVisit);  // 현재 시간이 visitDate 10분 전보다 이전인지 확인
//
//		if (!isVisitBeforeTenMinutes){
//			throw  new RuntimeException("방문 가능한 시간은 예약 시간 10분 전까지입니다. 지나친 시간이므로 방문을 처리할 수 없습니다.");
//		}

		return BookingDto.of(bookings.get(0));
	}
}
