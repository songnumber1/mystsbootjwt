package com.stsboot.jwt.model;

import java.util.Map;

import lombok.Data;

@Data
public class TokenError {
    String errorCode;

    String errorMsg;

    Map<String, String> extraInfo;
}