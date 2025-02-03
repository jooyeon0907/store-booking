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

	/**
	 * 회원가입 처리
	 * 	- 이미 등록된 이름이나 전화번호가 있다면 회원 가입 실패
	 * 	- 비밀번호 암호화하여 저장
	 */
	@Override
	public boolean register(SignInForm parameter) {
		Optional<Customer> optionalCustomer = customerRepository.findByNameOrPhone(parameter.getName(), parameter.getPhone());
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

	/**
	 *  Spring Security에서 사용자 정보를 로드하기 위해 구현하는 메서드
	 */
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
