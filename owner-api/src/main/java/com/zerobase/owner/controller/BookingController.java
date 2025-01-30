package com.zerobase.owner.controller;

import com.zerobase.domain.dto.common.BookingDto;
import com.zerobase.owner.model.BookingForm;
import com.zerobase.owner.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/store/booking")
@RequiredArgsConstructor
public class BookingController {

	private final BookingService bookingService;

	@GetMapping("/list")
	public String list(Model model, HttpServletRequest request) {
		addBookingListToModel(request, model);

		return "store/booking/list";
	}


	@PostMapping("/status/update")
	public String updateBookingStatus(Model model, HttpServletRequest request, BookingForm parameter) {

		boolean result = bookingService.updateBookingStatus(parameter);

		addBookingListToModel(request, model);

		return "redirect:/store/booking/list";
	}


	private Long getOwnerId(HttpServletRequest request) {
		return (Long) request.getSession().getAttribute("ownerId");
	}

	private void addBookingListToModel(HttpServletRequest request, Model model) {
		List<BookingDto> list = bookingService.list(getOwnerId(request));
		model.addAttribute("bookingList", list);
	}

}
