package com.zerobase.owner.controller;

import com.zerobase.domain.dto.common.StoreDto;
import com.zerobase.owner.repository.model.StoreInput;
import com.zerobase.owner.service.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;

	@GetMapping
	public String store(){
		return "store/index";
	}

	@GetMapping("/register")
	public String register() {
		return "store/register";
	}

	@PostMapping("/register")
	public String registerSubmit(Model model, HttpServletRequest request, StoreInput parameter) {
		Long ownerId = (Long) request.getSession().getAttribute("ownerId");

		boolean result = false;
		try{
   			result = storeService.register(ownerId, parameter);
	  	}catch (Exception e){
			model.addAttribute("errorMessage", e.getMessage());
			return "store/register";
	 	 }
		model.addAttribute("result", result);

		return "redirect:/store/detail";
	}

	@GetMapping("/detail")
	public String detail(Model model, HttpServletRequest request) {
		Long ownerId = (Long) request.getSession().getAttribute("ownerId");

		StoreDto store = null;
		try{
			store = storeService.detail(ownerId);
		}catch (Exception e){
		}
		model.addAttribute("store", store);

		return "store/detail";
	}


	@GetMapping("/edit")
	public String edit(Model model, HttpServletRequest request) {
		Long ownerId = (Long) request.getSession().getAttribute("ownerId");

		StoreDto store = null;
		try{
			store = storeService.detail(ownerId);
		}catch (Exception e){
		}
		model.addAttribute("store", store);

		return "store/edit";
	}

	@PostMapping("/update")
	public String update(StoreInput parameter) {
		boolean result = storeService.update(parameter);

		return "redirect:/store/detail";
	}

	@PostMapping("/delete")
	public String delete(StoreInput parameter) {
		boolean result = false;
		result = storeService.del(parameter.getId());
//		try{
//			result = storeService.del(parameter.getId());
//		}catch (Exception e){
//
//		}

		return "redirect:/store/detail";
	}




}
