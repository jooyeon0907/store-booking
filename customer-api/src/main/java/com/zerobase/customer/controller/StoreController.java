package com.zerobase.customer.controller;

import com.zerobase.customer.service.StoreService;
import com.zerobase.domain.dto.common.StoreDto;
import com.zerobase.domain.model.common.StoreParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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


}
