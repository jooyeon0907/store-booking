package com.zerobase.customer.service;

import com.zerobase.customer.model.SignInForm;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomerService extends UserDetailsService {

	boolean register(SignInForm parameter);
}
