package com.zerobase.customer.repository;

import com.zerobase.domain.entity.common.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByCustomerId(Long customerId);
	Optional<Booking> findByStoreIdAndVisitDate(Long storeId, LocalDateTime visitDate);

	@Query("SELECT b FROM Booking b WHERE b.store.id = :storeId AND b.customer.id = :customerId AND FUNCTION('DATE', b.visitDate) = :visitDate")
    Optional<Booking> findByStoreIdAndCustomerIdAndVisitDate(@Param("storeId") Long storeId,
                                                             @Param("customerId") Long customerId,
                                                             @Param("visitDate") LocalDate visitDate);

}
