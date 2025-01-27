package com.zerobase.owner.service;

import com.zerobase.owner.model.SignInForm;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface OwnerService extends UserDetailsService {

	boolean register(SignInForm parameter);

	long getOwnerId(String name);
}
