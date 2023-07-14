package com.stsboot.jwt.serviceImpl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.stsboot.jwt.Repository.UserRepository;
import com.stsboot.jwt.model.User;
import com.stsboot.jwt.properties.TokenProperties;
import com.stsboot.jwt.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenProperties tokenProperties;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String createAccessToken(User user) {
        if (user == null)
            return null;

        String subject = tokenProperties.getAccessSubject();
        long expired = tokenProperties.getAccessExpired();
        String claimUserId = tokenProperties.getClaimUserId();
        String clamiUserName = tokenProperties.getClaimUserName();
        String secert = tokenProperties.getAccessSecert();
        
        String accessToken = JWT.create()
				.withSubject(subject)
				.withExpiresAt(new Date(System.currentTimeMillis()+expired))
				.withClaim(claimUserId, user.getId())
				.withClaim(clamiUserName, user.getUsername())
        		.sign(Algorithm.HMAC512(secert));
        
        return accessToken;
    }

    @Override
    public String createRefreshToken(User user) {
        if (user == null)
            return null;

        String subject = tokenProperties.getRefreshSubject();
        long expired = tokenProperties.getRefreshExpired();
        String claimUserId = tokenProperties.getClaimUserId();
        String clamiUserName = tokenProperties.getClaimUserName();
        String secert = tokenProperties.getRefreshSecert();
        
        String refreshToken = JWT.create()
				.withSubject(subject)
				.withExpiresAt(new Date(System.currentTimeMillis()+expired))
				.withClaim(claimUserId, user.getId())
				.withClaim(clamiUserName, user.getUsername())
        		.sign(Algorithm.HMAC512(secert));
        
        return refreshToken;
    }

    @Override
    public User verfityAccessToken(String accessToken) {
        String secert = tokenProperties.getAccessSecert();
        String claimUserId = tokenProperties.getClaimUserId();
        //String clamiUserName = tokenProperties.getClaimUserName();
        String prefix = tokenProperties.getAccessPrefix();

        accessToken = accessToken.replace(prefix, "");

        long userId = JWT.require(Algorithm.HMAC512(secert)).build().verify(accessToken).getClaim(claimUserId)
                .asLong();

        // String userName = JWT.require(Algorithm.HMAC512(secert)).build().verify(accessToken).getClaim(clamiUserName)
        //         .asString();

        Optional<User> user = userRepository.findById(userId);

        return user.get();

        // if (accessToken == null) {
        //     throw new NullPointerException("AccessToken is null");
        // }
            
        // try
        // {
            
        // } 
        // catch (TokenExpiredException ex) {
        //     throw ex;
        // }
        // catch (Exception ex) {
        //     throw ex;
        // }
    }

    @Override
    public User verfityRefreshToken(String refreshToken) {
        if (refreshToken == null) {
            throw new NullPointerException("RefreshToken is null");
        }

        try
        {
            String secert = tokenProperties.getRefreshSecert();
            String claimUserId = tokenProperties.getClaimUserId();
            // String clamiUserName = tokenProperties.getClaimUserName();
            String prefix = tokenProperties.getRefreshPrefix();

            refreshToken = refreshToken.replace(prefix, "");

            long userId = JWT.require(Algorithm.HMAC512(secert)).build().verify(refreshToken).getClaim(claimUserId)
                    .asLong();

            // String userName = JWT.require(Algorithm.HMAC512(secert)).build().verify(refreshToken).getClaim(clamiUserName)
            //         .asString();

            Optional<User> user = userRepository.findById(userId);

            return user.get();
        } 
        catch (TokenExpiredException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw ex;
        }
    }
    
}
