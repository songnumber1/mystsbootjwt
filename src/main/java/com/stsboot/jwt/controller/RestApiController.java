package com.stsboot.jwt.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stsboot.jwt.Repository.UserRepository;
import com.stsboot.jwt.exception.AccountException;
import com.stsboot.jwt.exception.AccountExceptionType;
import com.stsboot.jwt.model.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RestApiController {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("home")
	public @ResponseBody String home() {
		return "<H1>Home</H1>";
	}
	
	@PostMapping("token")
	public @ResponseBody String token() {
		return "<H1>token</H1>";
	}
	
	@PostMapping("join")
	public @ResponseBody String join(@RequestBody User user) {
		
		User userEntity = userRepository.findByUsername(user.getUsername());
				
        if (userEntity != null){
            throw new AccountException(AccountExceptionType.DUPLICATED_USER);
        }
        
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles("ROLE_USER");
		userRepository.save(user);
		return "회원가입완료";
	}
	
	// user, manager, admin 권한만 접근 가능
	@PostMapping("/api/v1/user")
	public @ResponseBody String user() {
		return "user";
	}

	// manager, admin 권한만 접근 가능
	@PostMapping("/api/v1/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	// admin 권한만 접근 가능
	@PostMapping("/api/v1/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
}
