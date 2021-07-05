package com.stsboot.jwt.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stsboot.jwt.Repository.UserRepository;
import com.stsboot.jwt.model.User;

import lombok.RequiredArgsConstructor;


// http://localhost:8089/login
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService의 loadUserByUsername입니다.");
		
		User userEntity = userRepository.findByUsername(username);
		
		return new PrincipalDetails(userEntity);
	}
	
}
