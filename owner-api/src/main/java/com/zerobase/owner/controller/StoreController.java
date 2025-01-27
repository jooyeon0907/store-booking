package com.zerobase.owner.controller;

import com.zerobase.owner.model.StoreInput;
import com.zerobase.owner.model.StoreParam;
import com.zerobase.owner.service.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;

	@GetMapping
	public String store(){
		return "store/index";
	}

	@GetMapping("/register")
	public String register() {
		return "store/register";
	}

	@PostMapping("/register")
	public String registerSubmit(Model model, HttpServletRequest request, StoreInput parameter) {
		Long ownerId = (Long) request.getSession().getAttribute("ownerId");

		boolean result = false;
		try{
   			result = storeService.register(ownerId, parameter);
	  	}catch (Exception e){
	 	 }
		model.addAttribute("result", result);

		return "redirect:/store/info";
	}

	@GetMapping("/info")
	public String list(Model model, StoreParam parameter) {
		return "store/info";
	}



}
