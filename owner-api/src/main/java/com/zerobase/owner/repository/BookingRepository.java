package com.zerobase.owner.repository;

import com.zerobase.domain.entity.common.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByStoreId(Long storeId);
}
