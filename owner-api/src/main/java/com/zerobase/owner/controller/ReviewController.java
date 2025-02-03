package com.zerobase.owner.controller;

import com.zerobase.domain.dto.common.ReviewDto;
import com.zerobase.domain.model.common.ReviewParam;
import com.zerobase.owner.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/store/review")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	/**
	 * 리뷰 목록 조회
	 */
	@GetMapping("/list")
	public String list(Model model, HttpSession session, ReviewParam parameter) {
		List<ReviewDto> list = reviewService.list((Long) session.getAttribute("ownerId"));
		model.addAttribute("reviewList", list);

		return "store/review/list";
	}

	/**
	 * 리뷰 내용 조회
	 */
	@GetMapping("/detail")
	public String detail(Model model, ReviewParam parameter) {
		ReviewDto review = null;

		try{
			review = reviewService.getReview(parameter.getId());
		}catch (Exception e){

		}

		model.addAttribute("review", review);

		return "store/review/detail";
	}

	/**
	 * 리뷰 삭제
	 */
	@PostMapping("/delete")
	public String delete(ReviewParam parameter) {
		boolean result = false;

		try{
			result = reviewService.del(parameter.getId());
		}catch (Exception e){
		}

		return "redirect:/store/review/list";
	}

}
