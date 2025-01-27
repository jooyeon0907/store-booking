package com.zerobase.owner.service.impl;

import com.zerobase.owner.entity.Store;
import com.zerobase.owner.model.StoreInput;
import com.zerobase.owner.repository.OwnerRepository;
import com.zerobase.owner.repository.StoreRepository;
import com.zerobase.owner.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

	private final StoreRepository storeRepository;
	private final OwnerRepository ownerRepository;

	@Override
	public boolean register(Long ownerId, StoreInput parameter) {
		storeRepository.findByOwnerId(ownerId)
				.ifPresent(store -> {
					new RuntimeException("이미 매장을 등록하였습니다.");
				});
		storeRepository.findByName(parameter.getName())
				.ifPresent(store -> {
					new RuntimeException("이미 등록되어 있는 매장명입니다.");
				});

		Store store = Store.builder()
				.owner(ownerRepository.findById(ownerId).get())
				.name(parameter.getName())
				.location(parameter.getLocation())
				.description(parameter.getDescription())
				.build();
		storeRepository.save(store);

		return true;
	}
}
