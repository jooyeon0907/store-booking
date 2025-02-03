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
		return "redirect:/home";
	}

	@GetMapping("/login")
	public String login() {
		return "kiosk/login";
	}

	/**
	 * 키오스크에 점주 로그인
	 * 	- 로그인 성공 시, 점주 id, 매장 id, 매장 이름을 세션에 저장
	 */
	@PostMapping("/login")
	public String loginSubmit(Model model, HttpSession session, SignInForm parameter) {

		Long ownerId = -1L;
		StoreDto store = null;

		try{
			ownerId = kioskService.getOwnerId(parameter);
		}catch (Exception e){
			model.addAttribute("errorMessage", e.getMessage());
			return "kiosk/login";
		}

		try{
			store = kioskService.getStore(ownerId); // 매장이 등록되지 않은 점주는 로그인 실패
		}catch (Exception e){
			model.addAttribute("errorMessage", e.getMessage());
			return "kiosk/login";
		}

		session.setAttribute("ownerId", ownerId);
		session.setAttribute("storeId", store.getId());
		session.setAttribute("storeName", store.getName());

		return "redirect:/kiosk/home";
	}

	/**
	 * 키오스크에 점주 로그아웃
	 * 	- 로그아웃 성공 시, 세션 초기화
	 */
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // 세션 전체 무효화
		return "redirect:/kiosk/login";
	}

	/**
	 * 키오스크 홈으로 이동
	 * 	- 점주 로그인이 되지 않았다면 로그인 페이지로 리다이렉트
	 */
	@GetMapping("/home")
	public String home(Model model, HttpSession session, StoreParam parameter) {
    	Long ownerId = (Long) session.getAttribute("ownerId");
		if (ownerId == null) {
			return "redirect:/kiosk/login";
		}

		return "kiosk/home";
	}

	/**
	 * 예약 조회 요청
	 * - 조회 성공 시, 예약 조회 페이지로 이동
	 */
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

	/**
	 * 방문 처리 요청
	 */
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
