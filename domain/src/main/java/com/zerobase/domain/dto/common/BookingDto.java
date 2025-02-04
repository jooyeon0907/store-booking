package com.zerobase.domain.dto.common;

import com.zerobase.domain.entity.common.Booking;
import com.zerobase.domain.entity.common.BookingStatus;
import com.zerobase.domain.entity.common.Review;
import com.zerobase.domain.entity.common.Store;
import com.zerobase.domain.entity.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

	private Long id;
	private LocalDateTime bookingDate;
    private BookingStatus bookingStatus;
	private LocalDateTime visitDate;
    private boolean visitStatus;

	private Customer customer;
	private Store store;
	private Review review;

	public static BookingDto of(Booking booking) {
		return BookingDto.builder()
				.id(booking.getId())
				.bookingDate(booking.getBookingDate())
				.bookingStatus(booking.getBookingStatus())
				.visitDate(booking.getVisitDate())
				.visitStatus(booking.isVisitStatus())
				.customer(booking.getCustomer())
				.store(booking.getStore())
				.review(booking.getReview())
				.build();
	}

	public static List<BookingDto> of(List<Booking> bookings) {
		if (bookings != null) {
			List<BookingDto> bookingList = new ArrayList<>();
			for(Booking b : bookings) {
				bookingList.add(of(b));
			}
			return bookingList;
		}

		return null;
	}
}
