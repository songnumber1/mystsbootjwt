package com.stsboot.jwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/manager")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
public class ManagerController {
    @PostMapping("")
	public @ResponseBody String manager() {
		return "manager";
	}
}
