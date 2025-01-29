package com.zerobase.owner.service;

import com.zerobase.owner.repository.model.SignInForm;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface OwnerService extends UserDetailsService {

	boolean register(SignInForm parameter);

	Long getOwnerId(String name);
}
