package com.quangtoi.flowerstore.security;

import com.quangtoi.flowerstore.exception.FlowerApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String JwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long JwtExpirationDate;

    // generate JWT token
    public String generateToken(UserDetails userDetails){
        String username = userDetails.getUsername();
        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + JwtExpirationDate);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(JwtSecret)
        );
    }

    // get username from JWT token
    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        return username;
    }

    // validate token
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new FlowerApiException(HttpStatus.BAD_REQUEST, "Expired JWT Token");
        } catch (MalformedJwtException e) {
            throw new FlowerApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
        } catch (UnsupportedJwtException e) {
            throw new FlowerApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            throw new FlowerApiException(HttpStatus.BAD_REQUEST, "JWT Claims string is empty");
        }
    }
}
