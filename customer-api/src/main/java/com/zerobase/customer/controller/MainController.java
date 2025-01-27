package com.zerobase.customer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@GetMapping("/")
	public String index() {
		return "Hello";
	}

	@RequestMapping("/error/denied")
	public String errorDenied() {
		return "error/denied";
	}

}
