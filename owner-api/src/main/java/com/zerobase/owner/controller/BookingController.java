package com.zerobase.owner.controller;

import com.zerobase.owner.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store/booking")
@RequiredArgsConstructor
public class BookingController {

	private final StoreService storeService;

	@GetMapping
	public String store(){
		return "store/index";
	}

	@GetMapping("/list")
	public String register() {
		return "store/register";
	}





}
