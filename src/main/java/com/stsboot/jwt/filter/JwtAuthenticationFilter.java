package com.stsboot.jwt.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stsboot.jwt.auth.PrincipalDetails;
import com.stsboot.jwt.model.User;
import com.stsboot.jwt.properties.JwtProperties;

import lombok.RequiredArgsConstructor;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilte가 있음.
// /login 요청해서 username, password 전송하면 (post)
// UsernamePasswordAuthenticationFilte 동작함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	
	// /lgoin 요청을 하면 로그인 시도를 위해 실행되는 함수
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter : 로그인 시도중");
		
		// 1. username, password 받아서
		try {
//			BufferedReader br = request.getReader();
//			
//			String input = null;
//			while((input = br.readLine()) != null) {
//				System.out.println(input);
//			}
			
			ObjectMapper om = new ObjectMapper();
			User user = om.readValue(request.getInputStream(), User.class);
			System.out.println(user);
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			
			// PrincipalDetailsService의 loadUserByUsername() 함수가 실행된 후 정상이면 authentication이 리턴
			// DB에 있는 username과 password가 일치한다는 뜻
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			
			// authentication객체가 session영역에 저장됨 => 로그인이 되었다는 뜻
			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
			System.out.println(principalDetails.getUser().getUsername());
			
			// authentication객체가 session영역에 저장을 해야하고 그 방법이 return 해주면됨
			// 리턴의 이유는 권한 관리를 sercurity가 대신 해주기 때문에 편하려고 하는거임.
			// 굳이 JWT토큰을 사용하면서 세션을 만들 이유가 없음 . 근데 단지 권한 처리때문에 session에 저장함.
			return authentication;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	// attemptAuthentication실행 후 인증이 정상적으로 되었으면 successfulAuthentication가 실행된다.
	// JWT 토큰을 만들어 request를 요청한 클라이언트에게 JWT토큰을 response해주면된다.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication 실행됨 : 인증이완료되었습니다.");
		
		PrincipalDetails principalDetailis = (PrincipalDetails)authResult.getPrincipal();
		
		// RSA방식은 아니고 Hash암호 방식이다
		String jwtToken = JWT.create()
				.withSubject("cos")
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
				.withClaim(JwtProperties.JWT_CLAIM_ID, principalDetailis.getUser().getId())
				.withClaim(JwtProperties.JWT_CLAIM_USERNAME, principalDetailis.getUser().getUsername())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		
		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
	}
}
