package com.zerobase.kiosk.service.impl;

import com.zerobase.domain.entity.common.Store;
import com.zerobase.domain.entity.owner.Owner;
import com.zerobase.kiosk.model.SignInForm;
import com.zerobase.kiosk.repository.OwnerRepository;
import com.zerobase.kiosk.repository.StoreRepository;
import com.zerobase.kiosk.service.KioskService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KioskServiceImpl implements KioskService {

	private final OwnerRepository ownerRepository;
	private final StoreRepository storeRepository;


	@Override
	public Long login(SignInForm parameter) {

		Optional<Owner> optionalOwner = ownerRepository.findByName(parameter.getName());
		if (!optionalOwner.isPresent()) {
			throw new RuntimeException("회원 정보가 존재하지 않습니다.");
		}

		 if (!BCrypt.checkpw(parameter.getPassword(), optionalOwner.get().getPassword())) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다. 다시 시도해주세요.");
        }

		return optionalOwner.get().getId();
	}

	@Override
	public Long getStoreId(Long ownerId) {
		Store store = storeRepository.findByOwnerId(ownerId)
				.orElseThrow(() -> new RuntimeException("등록된 매장이 없습니다. \n 관리자 홈페이지에서 매장을 등록해 주세요."));
		return store.getId();
	}
}
