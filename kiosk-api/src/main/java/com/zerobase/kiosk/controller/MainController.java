package com.zerobase.kiosk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {

	@GetMapping("/")
	public String index() {
		return "redirect:/kiosk/home";
	}

}
