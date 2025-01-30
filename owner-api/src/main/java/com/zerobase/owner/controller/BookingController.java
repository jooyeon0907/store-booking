package com.zerobase.owner.controller;

import com.zerobase.domain.dto.common.BookingDto;
import com.zerobase.owner.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/store/booking")
@RequiredArgsConstructor
public class BookingController {

	private final BookingService bookingService;

	@GetMapping("/list")
	public String list(Model model, HttpServletRequest request) {
		Long ownerId = (Long) request.getSession().getAttribute("ownerId");
		List<BookingDto> list = bookingService.list(ownerId);

		model.addAttribute("bookingList", list);

		return "store/booking/list";
	}





}
