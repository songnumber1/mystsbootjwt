package com.stsboot.jwt.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.stsboot.com.util.Util;
import com.stsboot.jwt.Repository.UserRepository;
import com.stsboot.jwt.auth.PrincipalDetails;
import com.stsboot.jwt.model.TokenError;
import com.stsboot.jwt.model.User;
import com.stsboot.jwt.properties.TokenProperties;
import com.stsboot.jwt.service.TokenService;


// 시큐리티가 Filter를 가지고 있는데 그 중 BasicAuthenticationFilter 라는것이 있음
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어 있음.
// 만약에 권한이 인증이 필요한 주소가 아니라면 이 필터는 호출되지 않음
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

	@Value("{server.servlet.context-path}")
	private String contextPath;

	private TokenProperties tokenProperties;

	private TokenService tokenService;

	private UserRepository userRepository;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, TokenProperties tokenProperties, TokenService tokenService) {
		super(authenticationManager);
		this.userRepository = userRepository;
		this.tokenProperties = tokenProperties;
		this.tokenService = tokenService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("인증이나 권한이 필요한 주소 요청이 됨.");

		// Acccess 토큰을 검증해서 정상적인 사용자 확인
		String accessToken = request.getHeader(tokenProperties.getAccessHeader());

		System.out.println(request.getRequestURI());

		if (request.getRequestURI().contains(tokenProperties.getAnyRequestUri())) {
			chain.doFilter(request, response);
			return;
		}

		User user = this.tokenVerfity(request, response, accessToken, true);

		if (user == null) {
			// chain.doFilter(request, response);

			String refreshToken = request.getHeader(tokenProperties.getRefreshHeader());
			user = this.tokenVerfity(request, response, refreshToken, false);

			if (user == null) {
				return;
			}
			else {
				Optional<User> findUser = userRepository.findById(user.getId());

				if (findUser == null) {
					Map<String, String> extraInfo = new HashMap<String, String>();
					extraInfo.put("errorcode", tokenProperties.getTokenErrorCode());
					extraInfo.put("errorMsg", tokenProperties.getTokenErrorMsg());

					sendTokenErrorResponse(response,
						HttpStatus.UNAUTHORIZED.value(),
						"application/json",
						tokenProperties.getRefreshErrorCompareEqualsCode(),
						tokenProperties.getRefreshErrorCompareEqualsMsg(),
						extraInfo);
					return;
				}
			}
		}

		// 서명이 정상적으로 됨	
		// Acccess 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
		PrincipalDetails principalDetails = new PrincipalDetails(user);
		Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
				principalDetails.getAuthorities());

		// 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장.
		SecurityContextHolder.getContext().setAuthentication(authentication);

		chain.doFilter(request, response);
	}
	
	private User tokenVerfity(HttpServletRequest request, HttpServletResponse response, String token, boolean isAccess) {
		User user = null;

		if (token == null || token.equals("null")) {
			Map<String, String> extraInfo = new HashMap<String, String>();
			extraInfo.put("errorcode", tokenProperties.getTokenErrorCode());
			extraInfo.put("errorMsg", tokenProperties.getTokenErrorMsg());

			sendTokenErrorResponse(response,
				HttpStatus.UNAUTHORIZED.value(),
				"application/json",
				isAccess == true ? tokenProperties.getAccessErrorEmptyCode() : tokenProperties.getRefreshErrorEmptyCode(),
				isAccess == true ? tokenProperties.getAccessErrorEmptyMsg() : tokenProperties.getRefreshErrorEmptyMsg(),
				extraInfo);

			return user;
		}

		try {
			if (isAccess) {
				user = tokenService.verfityAccessToken(token);
			} else {
				user = tokenService.verfityRefreshToken(token);
			}

			return user;
		}
		catch (TokenExpiredException ex) {
			if (!isAccess) {
				Map<String, String> extraInfo = new HashMap<String, String>();
				extraInfo.put("errorcode", tokenProperties.getTokenErrorCode());
				extraInfo.put("errorMsg", tokenProperties.getTokenErrorMsg());

				sendTokenErrorResponse(response,
						HttpStatus.UNAUTHORIZED.value(),
						"application/json",
						tokenProperties.getRefreshErrorExpiredCode(),
						tokenProperties.getRefreshErrorExpiredMsg(),
						extraInfo);

				return user;
			}
			else {
				return null;
			}
		}
		catch (Exception ex) {
			Map<String, String> extraInfo = new HashMap<String, String>();
			extraInfo.put("errorcode", tokenProperties.getTokenErrorCode());
			extraInfo.put("errorMsg", tokenProperties.getTokenErrorMsg());

			sendTokenErrorResponse(response,
				HttpStatus.UNAUTHORIZED.value(),
				"application/json",
				isAccess == true ? tokenProperties.getAccessErrorVerfityCode() : tokenProperties.getRefreshErrorVerfityCode(),
				isAccess == true ? tokenProperties.getAccessErrorVerfityMsg() : tokenProperties.getRefreshErrorVerfityMsg(),
				extraInfo);

			return user;
		}
	}
	
	private void sendTokenErrorResponse(HttpServletResponse response, int httpStatus, String contentType,
										String errorCode, String errorMsg, Map<String, String> extraInfos) {
		response.setStatus(httpStatus);
		response.setContentType(contentType);

		TokenError tokenError = new TokenError();
		tokenError.setErrorCode(errorCode);
		tokenError.setErrorMsg(errorMsg);

		if (extraInfos != null) {
			tokenError.setExtraInfo(extraInfos);
		}

		PrintWriter out;

		try {
			out = response.getWriter();
		} catch (IOException e) {
			return;
		}

		try {
			out.println(Util.ObjectToJson(tokenError));
		}
		finally
		{
			out.close();
			out.flush();
		}
	}
	
	private void sendTokenErrorResponse(HttpServletResponse response, int httpStatus, String contentType, Object tokenError) {
		response.setStatus(httpStatus);
		response.setContentType(contentType);

		PrintWriter out;

		try {
			out = response.getWriter();
		} catch (IOException e) {
			return;
		}

		try {
			out.println(Util.ObjectToJson(tokenError));
		}
		finally
		{
			out.close();
			out.flush();
		}
	} 
}
