
package com.zerobase.kiosk.repository;

import com.zerobase.domain.entity.common.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByCustomerIdAndStoreId(Long customerId, Long storeId);
}
