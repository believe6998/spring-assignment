package com.love.start.auth;

import com.love.start.dto.AccountAuthDTO;
import com.love.start.entity.Account;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(JwtTokenProvider.class.getSimpleName());

    @Value("${jwt.secret}")
    private String JWT_SECRET;
    @Value("${jwt.expiration}")
    private Long JWT_EXPIRATION;
    @Value("${jwt.secret.prefix}")
    private String JWT_SECRET_PREFIX;

    public String GenerateToken(AccountAuthDTO account) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        String jwt = Jwts.builder()
                .setSubject(account.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();

        return JWT_SECRET_PREFIX + jwt;
    }

    public String GetUserNameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();

//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
//
//        return Jwts.builder()
//                .setSubject(authentication.getName())
//                .claim(AUTHORITIES_KEY, authorities)
//                .signWith(SignatureAlgorithm.HS512, key)
//                .setExpiration(validity)
//                .compact();
    }

    public boolean ValidateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            LOGGER.warning("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            LOGGER.warning("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            LOGGER.warning("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            LOGGER.warning("JWT claims string is empty.");
        }
        return false;
    }
}
