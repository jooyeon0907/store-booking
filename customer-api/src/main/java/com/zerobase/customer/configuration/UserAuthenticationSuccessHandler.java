package com.zerobase.customer.configuration;


import com.zerobase.customer.service.CustomerService;
import com.zerobase.owner.service.OwnerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler extends  SimpleUrlAuthenticationSuccessHandler {

    private final CustomerService customerService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
	 	System.out.println("로그인 성공!");

	 	User userDetails = (User) authentication.getPrincipal();
		request.getSession().setAttribute("customerId", customerService.getId(userDetails.getUsername()));  // 세션에 저장

        response.sendRedirect("/store/list");  // 홈 페이지로 리다이렉트
	}
}
