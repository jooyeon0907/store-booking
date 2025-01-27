package com.zerobase.owner.repository;

import com.zerobase.owner.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
	Optional<Store> findByName(String name);
	Optional<Store> findByOwnerId(Long ownerId);
}
