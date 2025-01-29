package com.zerobase.customer.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {
	// 모든 컨트롤러에(CustomerController, StoreController) 적용하기 위해 @ControllerAdvice 사용

	// 모든 페이지에 공통으로 적용할 메서드
    @ModelAttribute
    public void addCustomerIdToModel(HttpServletRequest request, Model model) {
        Long customerId = (Long) request.getSession().getAttribute("customerId");
        model.addAttribute("customerId", customerId);  // 모든 뷰에 customerId를 자동으로 추가
    }
}