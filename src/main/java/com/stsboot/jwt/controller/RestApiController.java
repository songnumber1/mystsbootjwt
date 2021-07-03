package com.stsboot.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

	@GetMapping("home")
	public @ResponseBody String home() {
		return "<H1>Home</H1>";
	}
	
	@PostMapping("token")
	public @ResponseBody String token() {
		return "<H1>token</H1>";
	}
}
