package com.zerobase.owner.service;


import com.zerobase.domain.dto.common.StoreDto;
import com.zerobase.owner.repository.model.StoreInput;


public interface StoreService{

	StoreDto detail(Long ownerId);

	boolean register(Long ownerId, StoreInput parameter);

	boolean del(Long id);

	boolean update(StoreInput id);
}
