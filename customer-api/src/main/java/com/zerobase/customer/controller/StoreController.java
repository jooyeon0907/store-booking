package com.zerobase.customer.controller;

import com.zerobase.customer.dto.BookingDto;
import com.zerobase.customer.model.BookingForm;
import com.zerobase.customer.service.StoreService;
import com.zerobase.owner.dto.StoreDto;
import com.zerobase.owner.repository.model.StoreParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;

	@GetMapping
	public String customer(){
		return "store/index";
	}

	@GetMapping("/list")
	public String list(Model model) {
		List<StoreDto> list = storeService.list();
		model.addAttribute("storeList", list);
		return "store/list";
	}

	@GetMapping("/detail")
	public String detail(Model model, StoreParam parameter) {

		StoreDto store = storeService.detail(parameter.getId());
		model.addAttribute("store", store);

		return "store/detail";
	}

	@GetMapping("/booking")
	public String booking(Model model, BookingForm parameter) {
		model.addAttribute("store", storeService.detail(parameter.getStoreId()));

		return "store/booking";
	}

	@PostMapping("/booking")
	public String bookingComplete(Model model, BookingForm parameter) {
        boolean result = false;

		String errorMessage = "예약이 실패되었습니다.";
		try{
			result = storeService.createBooking(parameter);
		}catch (Exception e){
		  errorMessage = e.getMessage();
		}

		if (!result) {
			model.addAttribute("store", storeService.detail(parameter.getStoreId()));
        	model.addAttribute("errorMessage", errorMessage);
			return "store/booking";
		}
        model.addAttribute("message", "예약이 완료되었습니다!");
		return "store/index";
//		return "store/booking_list";
	}




}
