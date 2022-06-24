package com.example.rest.security.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.rest.security.models.Role;
import com.example.rest.security.models.User;
import com.example.rest.security.properties.JWTProperties;
import com.example.rest.security.service.JWTService;
import com.example.rest.security.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class JWTServiceImpl implements JWTService {

    private final UserService userService;
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JWTServiceImpl(JWTProperties jwtProperties, UserService userService) {
        this.userService = userService;
        this.algorithm = Algorithm.HMAC256(jwtProperties.getSecret().getBytes());
        this.verifier = JWT.require(algorithm).build();
    }

    @Override
    public String generateAccessToken(User user, String url) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(url)
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(algorithm);
    }

    @Override
    public String generateRefreshToken(User user, String url) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 120 * 60 * 1000))
                .withIssuer(url)
                .sign(algorithm);
    }

    @Override
    public Map<String, String> generateTokensMap(String accessToken, String refreshToken) {
        return new HashMap<>() {{
            put("access_token", accessToken);
            put("refresh_token", refreshToken);
        }};
    }

    @Override
    public Map<String, String> refreshToken(String authorizationHeader, String url) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);

                String accessToken = generateAccessToken(user, url);
                return generateTokensMap(accessToken, refreshToken);
            } catch (Exception exception) {
                return new HashMap<>() {{
                    put("error_message", exception.getMessage());
                }};
            }
        } else
            throw new RuntimeException("Refresh token is missing");
    }

    @Override
    public DecodedJWT decodeToken(String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        return verifier.verify(token);
    }

    @Override
    public Collection<SimpleGrantedAuthority> extractAuthorities(DecodedJWT decodedJWT) {
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return authorities;
    }
}
