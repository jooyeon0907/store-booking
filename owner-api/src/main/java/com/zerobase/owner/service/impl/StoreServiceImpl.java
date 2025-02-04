package com.zerobase.owner.service.impl;

import com.zerobase.domain.dto.common.StoreDto;
import com.zerobase.domain.entity.common.Store;
import com.zerobase.owner.repository.model.StoreInput;
import com.zerobase.owner.repository.OwnerRepository;
import com.zerobase.owner.repository.StoreRepository;
import com.zerobase.owner.service.StoreService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
	@PersistenceContext
    private EntityManager entityManager;

	private final StoreRepository storeRepository;
	private final OwnerRepository ownerRepository;

	@Override
	public StoreDto detail(Long ownerId) {
		Store store = storeRepository.findByOwnerId(ownerId)
				.orElseThrow(() -> new RuntimeException("등록된 매장이 없습니다."));
		return StoreDto.of(store);
	}

	@Override
	public boolean register(Long ownerId, StoreInput parameter) {
		// 점주는 1개의 매장만 등록할 수 있으므로, 해당 점주가 등록된 매장이 있으면 실패 처리
		storeRepository.findByOwnerId(ownerId)
				.ifPresent(store -> {
					throw new RuntimeException("이미 매장을 등록하였습니다.");
				});
		// 이미 등록된 이름은 실패 처리
		storeRepository.findByName(parameter.getName())
				.ifPresent(store -> {
					throw new RuntimeException("이미 등록되어 있는 매장명입니다.");
				});

		Store store = Store.builder()
				.owner(ownerRepository.findById(ownerId).get())
				.name(parameter.getName())
				.location(parameter.getLocation())
				.description(parameter.getDescription())
				.openTime(parameter.getOpenTime())
				.closeTime(parameter.getCloseTime())
				.build();
		storeRepository.save(store);

		return true;
	}

	@Override
	public boolean del(Long id) {
		Store store = storeRepository.findById(id).orElseThrow();
		store.setOwner(null);
		storeRepository.flush();
		entityManager.clear();
		storeRepository.delete(store);
		return true;
	}

	@Override
	public boolean update(StoreInput parameter) {
		Optional<Store> optionalStore = storeRepository.findById(parameter.getId());
		if (optionalStore.isPresent()) {
			Store store = optionalStore.get();
			store.setName(parameter.getName());
			store.setLocation(parameter.getLocation());
			store.setDescription(parameter.getDescription());
			store.setOpenTime(parameter.getOpenTime());
			store.setCloseTime(parameter.getCloseTime());
			storeRepository.save(store);
			return true;
		}

		return false;
	}
}
