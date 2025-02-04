package com.zerobase.customer.repository;

import com.zerobase.domain.entity.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByName(String name);

	Optional<Customer> findByNameOrPhone(String name, String phone);
}
