package com.zerobase.owner.controller;

import com.zerobase.owner.model.SignInForm;
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
		Long ownerId = (Long) request.getSession().getAttribute("ownerId");

        // ownerId를 이용해 필요한 처리를 합니다.
        System.out.println("Logged in Owner ID: " + ownerId);
		return "owner/index";
	}

	@GetMapping("/register")
	public String register() {
		return "owner/register";
	}

	@PostMapping("/register")
	public String registerSubmit(Model model, HttpServletRequest request, SignInForm parameter) {

		boolean result = ownerService.register(parameter);
		model.addAttribute("result", result);

		return "owner/register_complete";
	}

	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		Long ownerId = (Long) request.getSession().getAttribute("ownerId");

        System.out.println("login in Owner ID: " + ownerId);
		return "owner/login";
	}


}
