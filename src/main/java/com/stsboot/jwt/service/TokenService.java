package com.stsboot.jwt.service;

import com.stsboot.jwt.model.User;

public interface TokenService {
    String createAccessToken(User user);

    String createRefreshToken(User user);

    User verfityAccessToken(String accessToken);

    User verfityRefreshToken(String refreshToken);
}
