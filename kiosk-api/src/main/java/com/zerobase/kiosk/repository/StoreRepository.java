package com.zerobase.kiosk.repository;

import com.zerobase.domain.entity.common.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
	Optional<Store> findByOwnerId(Long ownerId);
}
