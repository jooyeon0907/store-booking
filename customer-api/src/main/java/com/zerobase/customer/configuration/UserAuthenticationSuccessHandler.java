package com.zerobase.customer.configuration;


import com.zerobase.customer.service.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

// 사용자 인증 성공 핸들러
@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler extends  SimpleUrlAuthenticationSuccessHandler {

    private final CustomerService customerService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
	 	System.out.println("로그인 성공!");

	 	User userDetails = (User) authentication.getPrincipal();
		request.getSession().setAttribute("customerId", customerService.getId(userDetails.getUsername()));  // 로그아웃 성공 시, 세션에 고객 id 저장

        response.sendRedirect("/store/list");  // 홈 페이지로 리다이렉트
	}
}
