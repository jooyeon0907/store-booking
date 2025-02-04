package com.zerobase.owner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {

	@GetMapping("/")
	public String index() {
		return "redirect:/owner";
	}

	@RequestMapping("/error/denied")
	public String errorDenied() {
		return "error/denied";
	}

}
