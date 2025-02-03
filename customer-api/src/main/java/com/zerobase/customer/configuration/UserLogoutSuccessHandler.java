package com.zerobase.customer.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

// 사용자 로그아웃 성공 핸들러
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		// 로그아웃 성공 시, 세션에 고객 id 삭제
        request.getSession().removeAttribute("customerId");
        response.sendRedirect("/customer");
	}
}
