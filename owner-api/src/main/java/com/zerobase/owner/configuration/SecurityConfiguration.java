package com.zerobase.owner.configuration;

import com.zerobase.owner.service.OwnerService;
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
public class SecurityConfiguration {

    private final OwnerService ownerService;

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
		return new UserAuthenticationSuccessHandler(ownerService);
	}

	@Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new UserLogoutSuccessHandler();  // 커스텀 로그아웃 성공 핸들러
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 보호 비활성화
        http.csrf(csrf -> csrf.disable());

        // 페이지의 권한 설정
        http.authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
            .requestMatchers("/",
                    "/owner/register",
                    "/owner/login/**"
                    )
            .permitAll() // 모든 페이지에 접근 권한 허용
            .anyRequest()
            .authenticated()
        ); // 나머지 요청은 인증 필요

        // 로그인 설정
        http.formLogin(formLogin ->
            formLogin
            .loginPage("/owner/login")
            .usernameParameter("name")
            .successHandler(getSuccessHandler())
            .failureHandler(getFailureHandler())
            .permitAll()
        );

        // 로그아웃 설정
        http.logout(logout ->
            logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/owner/logout"))
			.logoutSuccessHandler(logoutSuccessHandler())
            .invalidateHttpSession(true)
        );

        // 예외 처리 (권한 부족 시 페이지)
        http.exceptionHandling(exceptionHandling -> exceptionHandling
            .accessDeniedPage("/templates/error/denied")
        );

//        http.sessionManagement(sessionManagement -> sessionManagement
//            .invalidSessionUrl("/login?expired=true")  // 세션이 만료되면 이 URL로 리다이렉트
//            .maximumSessions(1)
//            .expiredUrl("/login?expired=true")
//        );

        return http.build();
    }



}
