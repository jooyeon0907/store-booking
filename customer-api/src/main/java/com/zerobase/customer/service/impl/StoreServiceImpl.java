package com.zerobase.customer.service.impl;

import com.zerobase.customer.service.StoreService;
import com.zerobase.owner.dto.StoreDto;
import com.zerobase.owner.entity.Store;
import com.zerobase.owner.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

	private final StoreRepository storeRepository;

	private Sort getSortBySortValueDesc() {
		return Sort.by(Sort.Direction.DESC, "sortValue");
	}

	@Override
	public List<StoreDto> list() {
		List<Store> stores = storeRepository.findAll();
		return StoreDto.of(stores);
	}
}
