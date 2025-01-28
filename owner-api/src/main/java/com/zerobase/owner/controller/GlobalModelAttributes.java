package com.zerobase.owner.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalModelAttributes {
	// 모든 컨트롤러에(OwnerController, StoreController) 적용하기 위해 @ControllerAdvice 사용

	// 모든 페이지에 공통으로 적용할 메서드
    @ModelAttribute
    public void addOwnerIdToModel(HttpServletRequest request, Model model) {
        Long ownerId = (Long) request.getSession().getAttribute("ownerId");
        model.addAttribute("ownerId", ownerId);  // 모든 뷰에 ownerId를 자동으로 추가
    }
}