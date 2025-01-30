package com.zerobase.customer.service.impl;

import com.zerobase.customer.model.SignInForm;
import com.zerobase.customer.repository.CustomerRepository;
import com.zerobase.customer.service.CustomerService;
import com.zerobase.domain.entity.customer.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	@Override
	public boolean register(SignInForm parameter) {
		Optional<Customer> optionalCustomer = customerRepository.findByName(parameter.getName());
		if (optionalCustomer.isPresent()){
			return false;
		}

		String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());

		Customer customer = Customer.builder()
				.name(parameter.getName())
				.password(encPassword)
				.phone(parameter.getPhone())
				.build();
		customerRepository.save(customer);

		return true;
	}

	@Override
	public Long getId(String name) {
		return customerRepository.findByName(name).get().getId();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Customer> optionalCustomer = customerRepository.findByName(username);
		if (!optionalCustomer.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}

		Customer customer = optionalCustomer.get();
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		return new User(customer.getName(), customer.getPassword(), grantedAuthorities);
	}
}
