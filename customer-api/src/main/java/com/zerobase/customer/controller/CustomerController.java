package com.zerobase.customer.controller;

import com.zerobase.customer.model.SignInForm;
import com.zerobase.customer.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@GetMapping
	public String customer(){
		return "customer/index";
	}

	@GetMapping("/register")
	public String register() {
		return "customer/register";
	}

	@PostMapping("/register")
	public String registerSubmit(Model model, HttpServletRequest request, SignInForm parameter) {

		boolean result = customerService.register(parameter);
		model.addAttribute("result", result);

		return "customer/register_complete";
	}

	@GetMapping("/login")
	public String login() {
		return "customer/login";
	}



}
