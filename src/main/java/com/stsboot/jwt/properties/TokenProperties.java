package com.stsboot.jwt.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Configuration
@Getter
@PropertySource(value = "classpath:tokenProperty.properies")
public class TokenProperties {
    @Value("${jwt.claim.userid}")
    private String claimUserId;

    @Value("${jwt.claim.username}")
    private String claimUserName;


    @Value("${jwt.access.secert}")
    private String accessSecert;

    @Value("${jwt.access.subject}")
    private String accessSubject;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.access.expired}")
    private long accessExpired;

    @Value("${jwt.refresh.prefix}")
    private String accessPrefix;

    
    @Value("${jwt.refresh.secert}")
    private String refreshSecert;

    @Value("${jwt.refresh.subject}")
    private String refreshSubject;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    @Value("${jwt.refresh.expired}")
    private long refreshExpired;

    @Value("${jwt.refresh.prefix}")
    private String refreshPrefix;
}
