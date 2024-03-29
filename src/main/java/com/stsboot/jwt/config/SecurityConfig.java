package com.stsboot.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.stsboot.jwt.filter.JwtAuthenticationFilter;
import com.stsboot.jwt.properties.TokenProperties;
import com.stsboot.jwt.service.TokenService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private final TokenProperties tokenProperties;

	@Autowired
	private final TokenService tokenService;
	
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
		// .and()
				// .cors().configurationSource(corsConfig.corsFilter())
		.and()
		.formLogin().disable()
		.httpBasic().disable()
		.addFilter(new JwtAuthenticationFilter(authenticationManager(), tokenProperties, tokenService)) // AuthenticationManager가 파라메터로 넘겨야 한다.
		// .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository, tokenProperties, tokenService)) // AuthenticationManager가 파라메터로 넘겨야 한다.
		.authorizeRequests()
		// .antMatchers("/api/v1/user/**")
		// .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
		// .antMatchers("/api/v1/manager/**")
		// .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
		// .antMatchers("/api/v1/admin/**")
		// .access("hasRole('ROLE_ADMIN')")
			// .antMatchers(tokenProperties.getAnyRequestUri() + "/**").permitAll()
		.anyRequest().permitAll();
	}
}
