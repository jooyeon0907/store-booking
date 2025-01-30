package com.zerobase.customer.controller;

import com.zerobase.domain.dto.common.BookingDto;
import com.zerobase.customer.model.BookingForm;
import com.zerobase.customer.service.BookingService;
import com.zerobase.customer.service.StoreService;
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

	private final StoreService storeService;
	private final BookingService bookingService;

	@GetMapping
	public String customer(){
		return "store/booking/index";
	}

	@GetMapping("/create")
	public String booking(Model model, BookingForm parameter) {
		model.addAttribute("store", storeService.detail(parameter.getStoreId()));

		return "store/booking/create";
	}

	@PostMapping("/create")
	public String bookingComplete(Model model, BookingForm parameter) {
        boolean result = false;

		String errorMessage = "예약이 실패되었습니다.";
		try{
			result = bookingService.create(parameter);
		}catch (Exception e){
		  errorMessage = e.getMessage();
		}

		if (!result) {
			model.addAttribute("store", storeService.detail(parameter.getStoreId()));
        	model.addAttribute("errorMessage", errorMessage);
			return "store/booking/create";
		}

        model.addAttribute("bookingMessage", "예약이 완료되었습니다! 예약 목록에서 확인해 주세요.");
		model.addAttribute("store", storeService.detail(parameter.getStoreId()));
		return "store/detail";
	}

	@GetMapping("/list")
	public String list(Model model, HttpServletRequest request) {
		Long customerId = (Long) request.getSession().getAttribute("customerId");
		List<BookingDto> list = bookingService.list(customerId);

		model.addAttribute("bookingList", list);
		return "store/booking/list";
	}




}
