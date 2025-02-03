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

	/**
	 * 매장 등록 페이지로 이동
	 */
	@GetMapping("/register")
	public String register() {
		return "store/register";
	}

	/**
	 * 매장 등록 처리
	 * 	- 등록 실패 시, 매장 등록 페이지로 이동
	 * 	- 성공 시, 매장 상세 페이지로 리다이렉트
	 */
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

	/**
	 * 매장 상세 페이지로 이동
	 */
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

	/**
	 * 매장 수정 페이지로 이동
	 */
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

	/**
	 * 매장 수정 내용 업데이트 처리
	 */
	@PostMapping("/update")
	public String update(StoreInput parameter) {
		boolean result = storeService.update(parameter);

		return "redirect:/store/detail";
	}

	/**
	 * 매장 삭제 처리
	 */
	@PostMapping("/delete")
	public String delete(StoreInput parameter) {
		boolean result = false;

		try{
			result = storeService.del(parameter.getId());
		}catch (Exception e){

		}

		return "redirect:/store/detail";
	}




}
