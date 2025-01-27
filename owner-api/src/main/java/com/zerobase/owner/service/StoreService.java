package com.zerobase.owner.service;


import com.zerobase.owner.model.StoreInput;

public interface StoreService{
	boolean register(Long ownerId, StoreInput parameter);
}
