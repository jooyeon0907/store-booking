package com.zerobase.owner.service.impl;

import com.zerobase.owner.entity.Store;
import com.zerobase.owner.model.StoreInput;
import com.zerobase.owner.repository.StoreRepository;
import com.zerobase.owner.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

	private final StoreRepository storeRepository;

	@Override
	public boolean register(StoreInput parameter) {

		Optional<Store> optionalStore = storeRepository.findByName(parameter.getName());
		if (optionalStore.isPresent()) {
			return false;
		}


		return true;
	}
}
