package com.zerobase.customer.configuration;

import com.zerobase.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {  // Spring Security 를 이용한 로그인/로그아웃 구현

    private final CustomerService customerService;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
	UserAuthenticationFailureHandler getFailureHandler() {
		return new UserAuthenticationFailureHandler();
	}

    @Bean
    UserAuthenticationSuccessHandler getSuccessHandler() {
		return new UserAuthenticationSuccessHandler(customerService);
	}

	@Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new UserLogoutSuccessHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 보호 비활성화
        http.csrf(csrf -> csrf.disable());

        // 페이지의 권한 설정
        http.authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
            .requestMatchers("/",
                    "/customer/register",
                    "/customer/login/**",
                    "/store/list",
                    "/store/detail"
                    )
            .permitAll() // 모든 페이지에 접근 권한 허용
            .anyRequest()
            .authenticated()
        ); // 나머지 요청은 인증 필요

        // 로그인 설정
        http.formLogin(formLogin ->
            formLogin
            .loginPage("/customer/login")
            .usernameParameter("name")
            .successHandler(getSuccessHandler())
            .failureHandler(getFailureHandler())
            .permitAll()
        );

        // 로그아웃 설정
        http.logout(logout ->
            logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/customer/logout"))
			.logoutSuccessHandler(logoutSuccessHandler())
            .invalidateHttpSession(true)
        );

        // 예외 처리 (권한 부족 시 페이지)
        http.exceptionHandling(exceptionHandling -> exceptionHandling
            .accessDeniedPage("/error/denied")
        );


        return http.build();
    }



}
