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
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

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
		if (ownerId == null) {
			return "redirect:/kiosk/login";
		}

		return "kiosk/home";
	}

	@PostMapping("/booking/info")
	public String bookingInfo(Model model, BookingForm parameter) {

		Long customerId = -1L;
		BookingDto booking = null;

		try{
			customerId = kioskService.getCustomerId(parameter.getPhone());
			booking = kioskService.getBooking(customerId, parameter);
		}catch (Exception e){
			model.addAttribute("errorMessage", e.getMessage());
			return "/kiosk/home";
		}

		model.addAttribute("booking", booking);

		return "kiosk/booking/info";
	}


	@PostMapping("/booking/visit/update")
	@ResponseBody //  JSON 응답을 반환하기 위한 어노테이션
	public Map<String, Object> visitStatusUpdate(BookingForm parameter) {
		Map<String, Object> response = new HashMap<>();

		try {
			kioskService.visitStatusUpdate(parameter.getId());
			response.put("success", true);
			response.put("message", "방문 완료 처리되었습니다.");
		} catch (Exception e) {
			response.put("success", false);
			response.put("errorMessage", e.getMessage());
		}

		return response;  // JSON 응답 반환
	}


}
