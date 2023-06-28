package com.stsboot.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.stsboot.jwt.Repository.UserRepository;
import com.stsboot.jwt.auth.PrincipalDetails;
import com.stsboot.jwt.model.User;
import com.stsboot.jwt.properties.TokenProperties;
import com.stsboot.jwt.service.TokenService;

// 시큐리티가 Filter를 가지고 있는데 그 중 BasicAuthenticationFilter 라는것이 있음
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어 있음.
// 만약에 권한이 인증이 필요한 주소가 아니라면 이 필터는 호출되지 않음
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

	private UserRepository userRepository;

	private TokenProperties tokenProperties;

	private TokenService tokenService;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, TokenProperties tokenProperties, TokenService tokenService) {
		super(authenticationManager);
		// TODO Auto-generated constructor stub
		
		this.userRepository = userRepository;
		this.tokenProperties = tokenProperties;
		this.tokenService = tokenService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("인증이나 권한이 필요한 주소 요청이 됨.");

		// Acccess 토큰을 검증해서 정상적인 사용자 확인
		String accessToken = request.getHeader(tokenProperties.getAccessHeader());
		
		User user = tokenService.verfityAccessToken(accessToken);

		if (user == null) {
			chain.doFilter(request, response);
		 	return;
		}
				
		// 서명이 정상적으로 됨	
		// Acccess 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
		PrincipalDetails principalDetails = new PrincipalDetails(user);
		Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
		
		// 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장.
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		chain.doFilter(request, response);
	}
}
