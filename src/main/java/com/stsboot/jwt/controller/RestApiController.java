package com.stsboot.jwt.controller;

import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stsboot.com.response.BaseResponseMessage;
import com.stsboot.com.response.StatusEnum;
import com.stsboot.com.util.util;
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
	public ResponseEntity<BaseResponseMessage> join(@RequestBody User user) {
		if(user.getUsername() == null 
			|| user.getUsername().isEmpty()
			|| user.getPassword() == null 
			|| user.getPassword().isEmpty())
		{
			throw new AccountException(AccountExceptionType.REUQIRED_PARAMETER_ERROR);
		}
		
		User userEntity = userRepository.findByUsername(user.getUsername());
				
        if (userEntity != null){
            throw new AccountException(AccountExceptionType.DUPLICATED_USER);
        }
        
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles("ROLE_USER");
		userRepository.save(user);
		//return "회원가입완료";
		
		return CreateReponse(StatusEnum.OK, "OK", null, null);
	}
	
	@PostMapping("resentity")
	public ResponseEntity<BaseResponseMessage> resentity() {
		BaseResponseMessage message = new BaseResponseMessage();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData("Data");

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}
	
	private ResponseEntity<BaseResponseMessage> CreateReponse(StatusEnum statusEnum, String message, String data, Map<String, String> headerData) {
		BaseResponseMessage responseMessage = new BaseResponseMessage();
        HttpHeaders headers= new HttpHeaders();
        
        if(headerData != null) {
        	headerData.forEach((key, value) -> {
        		if(value != null)
        			headers.add(key, value);
        	});
        };
        
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        responseMessage.setStatus(statusEnum);
        
        if(!util.IsNullEmpty(message))
        	responseMessage.setMessage(message);
        
        if(!util.IsNullEmpty(data))
        	responseMessage.setData("Data");

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
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
