// package com.stsboot.jwt.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import com.stsboot.jwt.properties.TokenProperties;

// @Configuration
// public class CorsConfig {

// 	@Autowired
// 	TokenProperties tokenProperties;

// 	@Bean
// 	public CorsConfigurationSource corsFilter() {
// 		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
// 		CorsConfiguration config = new CorsConfiguration();
// 		config.setAllowCredentials(true);
// 		// 이거 하면 CORS 안됨
// 		//config.addAllowedOrigin("*");
// 		config.addAllowedHeader("*");
// 		config.addAllowedMethod("*");
// 		config.addAllowedOriginPattern("*");
// 		config.addExposedHeader(tokenProperties.getAccessHeader());
// 		config.addExposedHeader(tokenProperties.getRefreshHeader());

// 		source.registerCorsConfiguration("/**", config);
// 		return source;
// 	}
// }
