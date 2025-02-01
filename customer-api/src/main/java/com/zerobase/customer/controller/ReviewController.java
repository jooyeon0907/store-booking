
package com.zerobase.customer.controller;

import com.zerobase.customer.service.ReviewService;
import com.zerobase.customer.service.StoreService;
import com.zerobase.domain.dto.common.ReviewDto;
import com.zerobase.domain.dto.common.StoreDto;
import com.zerobase.domain.entity.common.Review;
import com.zerobase.domain.model.common.ReviewParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer/review")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@GetMapping("/list")
	public String list(Model model, HttpSession session) {
		List<ReviewDto> list = reviewService.list((Long) session.getAttribute("customerId"));
		model.addAttribute("reviewList", list);

		return "customer/review/list";
	}

	@GetMapping("/write")
	public String review(Model model, ReviewParam parameter) {
		model.addAttribute("storeName", parameter.getStoreName());

		return "customer/review/write";
	}

	@PostMapping("/write")
	public String reviewSubmit(Model model, ReviewParam parameter) {
		ReviewDto review = null;

		try{
			review = reviewService.write(parameter);
		}catch (Exception e){

		}

		model.addAttribute("review", review);

		if (review == null) {
			model.addAttribute("review", "리뷰 작성에 실패 하였습니다.");
			return "customer/review/write";
		}

		return "customer/review/detail";
	}


	@GetMapping("/detail")
	public String detail(Model model, ReviewParam parameter) {
		ReviewDto review = null;

		try{
			review = reviewService.getReview(parameter.getId());
		}catch (Exception e){

		}

		model.addAttribute("review", review);

		return "customer/review/detail";
	}

	@GetMapping("/edit")
	public String edit(Model model, HttpServletRequest request,  ReviewParam parameter) {
		ReviewDto review = null;
		try{
			review = reviewService.getReview(parameter.getId());
		}catch (Exception e){
		}

		model.addAttribute("review", review);

		return "customer/review/edit";
	}

	@PostMapping("/update")
	public String update(ReviewParam parameter) {
		boolean result = reviewService.update(parameter);

		return "redirect:/customer/review/detail?id=" + parameter.getId();
	}

	@PostMapping("/delete")
	public String delete(ReviewParam parameter) {
		boolean result = false;

		try{
			result = reviewService.del(parameter.getId());
		}catch (Exception e){
			System.out.println("e : " + e.getMessage());

		}

		return "redirect:/customer/review/list";
	}

}
