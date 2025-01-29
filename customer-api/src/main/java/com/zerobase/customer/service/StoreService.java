package com.zerobase.customer.service;


import com.zerobase.owner.dto.StoreDto;

import java.util.List;

public interface StoreService{

	List<StoreDto> list();

	StoreDto detail(Long id);
}
