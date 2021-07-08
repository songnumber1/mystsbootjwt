package com.stsboot.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

import com.stsboot.jwt.Repository.UserRepository;
import com.stsboot.jwt.filter.JwtAuthenticationFilter;
import com.stsboot.jwt.filter.JwtAuthorizationFilter;
import com.stsboot.jwt.filter.MyFilter3;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CorsConfig corsConfig;
	
	@Autowired
	private final UserRepository userRepository;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder ();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// MyFilter3에서 Get Method은 제외하고 Post Method만 받기때문에 Get Method을 받을 경우 아래 http.addFilterBefore~~을 주석처리하자 
		//http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);
		
		http.csrf().disable();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.cors().configurationSource(corsConfig.corsFilter())
		.and()
		.formLogin().disable()
		.httpBasic().disable()
		.addFilter(new JwtAuthenticationFilter(authenticationManager())) // AuthenticationManager가 파라메터로 넘겨야 한다.
		.addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository)) // AuthenticationManager가 파라메터로 넘겨야 한다.
		.authorizeRequests()
		.antMatchers("/api/v1/user/**")
		.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
		.antMatchers("/api/v1/manager/**")
		.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
		.antMatchers("/api/v1/admin/**")
		.access("hasRole('ROLE_ADMIN')")
		.anyRequest().permitAll();
	}
}
