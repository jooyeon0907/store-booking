package com.zerobase.owner.service.impl;

import com.zerobase.owner.entity.Owner;
import com.zerobase.owner.model.SignInForm;
import com.zerobase.owner.repository.OwnerRepository;
import com.zerobase.owner.service.OwnerService;
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
public class OwnerServiceImpl implements OwnerService {

	private final OwnerRepository ownerRepository;

	@Override
	public boolean register(SignInForm parameter) {
		Optional<Owner> optionalOwner = ownerRepository.findByName(parameter.getName());
		if (optionalOwner.isPresent()){
			return false;
		}

		String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());

		Owner owner = Owner.builder()
				.name(parameter.getName())
				.password(encPassword)
				.build();
		ownerRepository.save(owner);

		return true;
	}

	@Override
	public Long getOwnerId(String name) {
		return ownerRepository.findByName(name).get().getId();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Owner> optionalOwner = ownerRepository.findByName(username);
		if (!optionalOwner.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}

		Owner owner = optionalOwner.get();
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		return new User(owner.getName(), owner.getPassword(), grantedAuthorities);
	}
}
