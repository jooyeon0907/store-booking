package com.zerobase.kiosk.controller;

import com.zerobase.domain.dto.common.BookingDto;
import com.zerobase.domain.dto.common.StoreDto;
import com.zerobase.domain.entity.customer.Customer;
import com.zerobase.domain.model.common.StoreParam;
import com.zerobase.kiosk.model.BookingForm;
import com.zerobase.kiosk.model.SignInForm;
import com.zerobase.kiosk.service.KioskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/kiosk")
@RequiredArgsConstructor
public class KioskController {

	private final KioskService kioskService;

	@GetMapping("/")
	public String index() {
		return "redirect:/kiosk/login";
	}

	@GetMapping("/login")
	public String login() {
		return "kiosk/login";
	}

	@PostMapping("/login")
	public String loginSubmit(Model model, HttpServletRequest request, HttpSession session, SignInForm parameter) {

		Long ownerId = -1L;
		StoreDto store = null;

		try{
			ownerId = kioskService.login(parameter);
		}catch (Exception e){
			model.addAttribute("errorMessage", e.getMessage());
			return "kiosk/login";
		}

		try{
			store = kioskService.getStore(ownerId);
		}catch (Exception e){
			model.addAttribute("errorMessage", e.getMessage());
			return "kiosk/login";
		}

		session.setAttribute("ownerId", ownerId);
		session.setAttribute("storeId", store.getId());
		session.setAttribute("storeName", store.getName());

		return "redirect:/kiosk/home";
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // 세션 전체 무효화
		return "redirect:/kiosk/login";
	}

	@GetMapping("/home")
	public String home(Model model, HttpSession session, StoreParam parameter) {
    	Long ownerId = (Long) session.getAttribute("ownerId");

		return "kiosk/home";
	}

	@PostMapping("/booking/info")
	public String bookingInfo(Model model, BookingForm parameter) {

		Long customerId = -1L;
		try{
			customerId = kioskService.getCustomerId(parameter.getPhone());
		}catch (Exception e){
			model.addAttribute("errorMessage", e.getMessage());
			return "redirect:/kiosk/home";
		}

		BookingDto booking = null;
		try{
			booking = kioskService.getBooking(customerId, parameter);
		}catch (Exception e){
			model.addAttribute("errorMessage", e.getMessage());
			return "/kiosk/home"; // 위 공통 로직 합치기 , String errorMessage
		}

		model.addAttribute("booking", booking);

		return "kiosk/booking/info";
	}

}

/*
home 에서 전화번호 입력 폼 받고 -> post /booking/info 요청
성공 시 예약내역조회는 get /booking/info

 */
