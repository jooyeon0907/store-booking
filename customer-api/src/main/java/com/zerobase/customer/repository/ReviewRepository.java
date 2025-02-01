
package com.zerobase.customer.repository;

import com.zerobase.domain.entity.common.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
//	List<Review> findByCustomerId(Long customerId);

    @Query("SELECT r FROM Review r WHERE r.booking.customer.id = :customerId")
    List<Review> findByCustomerId(@Param("customerId") Long customerId);

}
