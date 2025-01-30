package com.zerobase.customer.repository;

import com.zerobase.domain.entity.common.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	Optional<Booking> findByStoreIdAndVisitDate(Long storeId, LocalDateTime visitDate);
	List<Booking> findByCustomerId(Long customerId);
}
