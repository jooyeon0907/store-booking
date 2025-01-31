package com.zerobase.kiosk.controller;

import com.zerobase.domain.model.common.StoreParam;
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
		Long storeId = -1L;

		try{
			ownerId = kioskService.login(parameter);
		}catch (Exception e){
			model.addAttribute("errorMessage", e.getMessage());
			return "kiosk/login";
		}

		try{
			storeId = kioskService.getStoreId(ownerId);
		}catch (Exception e){
			model.addAttribute("errorMessage", e.getMessage());
			return "kiosk/login";
		}

		session.setAttribute("ownerId", ownerId);
		session.setAttribute("storeId", storeId);

		System.out.println("세션 저장 - ownerId: " + session.getAttribute("ownerId"));
		System.out.println("세션 저장 - storeId: " + session.getAttribute("storeId"));
		return "redirect:/kiosk/home";
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("ownerId");
		session.removeAttribute("storeId");

		return "redirect:/kiosk/login";
	}

	@GetMapping("/home")
	public String home(Model model, HttpSession session, StoreParam parameter) {
    	Long ownerId = (Long) session.getAttribute("ownerId");
		return "kiosk/home";
	}


}
