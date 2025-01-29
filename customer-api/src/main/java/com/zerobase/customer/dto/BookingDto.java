package com.zerobase.customer.dto;

import com.zerobase.customer.entity.Booking;
import com.zerobase.customer.entity.BookingStatus;
import com.zerobase.customer.entity.Customer;
import com.zerobase.owner.entity.Store;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	public static BookingDto of(Booking booking) {
		return BookingDto.builder()
				.id(booking.getId())
				.bookingDate(booking.getBookingDate())
				.visitDate(booking.getVisitDate())
				.bookingStatus(booking.getBookingStatus())
				.visitDate(booking.getVisitDate())
				.customer(booking.getCustomer())
				.store(booking.getStore())
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
