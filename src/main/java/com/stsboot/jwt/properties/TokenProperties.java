package com.stsboot.jwt.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Configuration
@Getter
@PropertySource(value = "classpath:tokenProperty.properies")
public class TokenProperties {

    // claim
    @Value("${jwt.claim.userid}")
    private String claimUserId;

    @Value("${jwt.claim.username}")
    private String claimUserName;

    // access Token
    @Value("${jwt.access.secert}")
    private String accessSecert;

    @Value("${jwt.access.subject}")
    private String accessSubject;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.access.expired}")
    private long accessExpired;

    @Value("${jwt.access.prefix}")
    private String accessPrefix;

    @Value("${jwt.access.expired.error.code}")
    private String accessErrorExpiredCode;

    @Value("${jwt.access.expired.error.msg}")
    private String accessErrorExpiredMsg;

    @Value("${jwt.access.empty.error.code}")
    private String accessErrorEmptyCode;

    @Value("${jwt.access.empty.error.msg}")
    private String accessErrorEmptyMsg;

    @Value("${jwt.access.verfity.error.code}")
    private String accessErrorVerfityCode;

    @Value("${jwt.access.verfity.error.msg}")
    private String accessErrorVerfityMsg;




    // refresh Token
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

    @Value("${jwt.refresh.expired.error.code}")
    private String refreshErrorExpiredCode;

    @Value("${jwt.refresh.expired.error.msg}")
    private String refreshErrorExpiredMsg;

    @Value("${jwt.refresh.empty.error.code}")
    private String refreshErrorEmptyCode;

    @Value("${jwt.refresh.empty.error.msg}")
    private String refreshErrorEmptyMsg;

    @Value("${jwt.refresh.verfity.error.code}")
    private String refreshErrorVerfityCode;

    @Value("${jwt.refresh.verfity.error.msg}")
    private String refreshErrorVerfityMsg;


    @Value("${jwt.refresh.compare.equals.error.code}")
    private String refreshErrorCompareEqualsCode;

    @Value("${jwt.refresh.compare.equals.error.msg}")
    private String refreshErrorCompareEqualsMsg;

    

    // common    
    @Value("${jwt.token.error.code}")
    private String tokenErrorCode;

    @Value("${jwt.token.error.msg}")
    private String tokenErrorMsg;



    @Value("${uri.anyRequest}")
    private String anyRequestUri;
}
