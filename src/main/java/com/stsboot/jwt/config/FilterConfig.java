package com.stsboot.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stsboot.jwt.Repository.UserRepository;
import com.stsboot.jwt.filter.JwtAuthorizationFilter;
import com.stsboot.jwt.filter.MyFilter1;
import com.stsboot.jwt.filter.MyFilter2;
import com.stsboot.jwt.properties.TokenProperties;
import com.stsboot.jwt.service.TokenService;

@Configuration
public class FilterConfig {
	@Autowired
	private TokenProperties tokenProperties;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserRepository userRepository;

	@Bean
	public FilterRegistrationBean<MyFilter1> filter1(){
		FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
		bean.addUrlPatterns("/*");
		bean.setOrder(1);
		
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<MyFilter2> filter2() {
		FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
		bean.addUrlPatterns("/*");
		bean.setOrder(0);

		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<JwtAuthorizationFilter> jwtAuthorizationFilter(){
		FilterRegistrationBean<JwtAuthorizationFilter> bean = 
		new FilterRegistrationBean<>(new JwtAuthorizationFilter(userRepository, tokenProperties, tokenService));
		bean.addUrlPatterns("/api/*");
		bean.setOrder(3);
		
		return bean;
	}
}
