package com.zerobase.kiosk.service;

import com.zerobase.kiosk.model.SignInForm;

public interface KioskService {
	Long login(SignInForm parameter);

	Long getStoreId(Long ownerId);
}
