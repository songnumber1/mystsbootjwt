package com.stsboot.jwt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFilter3 implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		HttpServletRequest req = (HttpServletRequest)request;
//		HttpServletResponse res = (HttpServletResponse)response;
//		
//		if(req.getMethod().equals("POST")) {
//			System.out.println("POST 요청됨");
//			String headerAuth = req.getHeader("Authorization");
//			System.out.println(headerAuth);
//			System.out.println("필터3");
//			
//			if(headerAuth.equals("cos")) {
//				chain.doFilter(req, res);
//			}else {
//				PrintWriter out = res.getWriter();
//				out.println("인증오류");
//			}
//		}
		
		System.out.println("필터3");
		chain.doFilter(request, response);
	}

}
