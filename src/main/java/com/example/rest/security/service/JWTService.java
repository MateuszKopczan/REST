package com.example.rest.security.service;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.rest.security.models.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Map;

public interface JWTService {

    String generateAccessToken(User user, String url);
    String generateRefreshToken(User user, String url);
    Map<String, String> generateTokensMap(String accessToken, String refreshToken);
    Map<String, String> refreshToken(String authorizationHeader, String url);
    DecodedJWT decodeToken(String authorizationHeader);
    Collection<SimpleGrantedAuthority> extractAuthorities(DecodedJWT decodedJWT);
}
