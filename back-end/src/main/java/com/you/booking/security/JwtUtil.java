package com.you.booking.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt-secret}")
    private String secret;

    public String generateToken(String email) {
        Date expireDate=new Date();
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH,1);
        return JWT.create()
                .withSubject("user details")
                .withClaim("email",email)
                .withExpiresAt(c.getTime())
                .withIssuer("visionaryCrofting/management")
                .sign(Algorithm.HMAC256(secret));
    }
    public String validateTokenAndRetrieveSubject(String token){
        JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(secret))
                .withSubject("user details")
                .withIssuer("visionaryCrofting/management")
                .build();
        DecodedJWT decodedJWT=jwtVerifier.verify(token);
        return decodedJWT.getClaim("email").asString();
    }
}
