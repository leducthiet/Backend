package com.DoAnTotNghiep.config.security;

import com.DoAnTotNghiep.config.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenProvider {

    private static final String JWT_SECRET = "4gG542F345sV";

    private static final long JWT_EXPIRATION = 604800000L;

    public static final String CLAIM_USERNAME_KEY_NAME = "username";

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_USERNAME_KEY_NAME, username);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiryDate)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Claims getUserFromJWT(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public void validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
        } catch (Exception ex) {
            throw new UnauthorizedException("chuỗi token không hợp lệ");
        }
    }
}
