package com.zerobase.owner.controller;

import com.zerobase.owner.repository.model.SignInForm;
import com.zerobase.owner.service.OwnerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owner")
@RequiredArgsConstructor
public class OwnerController {

	private final OwnerService ownerService;

	@GetMapping
	public String owner(HttpServletRequest request){
			return "owner/index";
	}

	/**
	 * 점주 회원가입 페이지로 이동
	 */
	@GetMapping("/register")
	public String register() {
		return "owner/register";
	}

	/**
	 * 점주 회원가입 처리
	 */
	@PostMapping("/register")
	public String registerSubmit(Model model, HttpServletRequest request, SignInForm parameter) {

		boolean result = ownerService.register(parameter);
		model.addAttribute("result", result);

		return "owner/register_complete";
	}

	/**
	 * 점주 로그인 페이지로 이동
	 */
	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		return "owner/login";
	}


}
