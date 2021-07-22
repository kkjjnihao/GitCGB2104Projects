package com.cy.jt.security.util;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static String secret = "hhhhhhh";

    /**
     *
     * @param map
     * @return
     */
    public static String generatorToken(Map<String,Object> map){
        return Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis()+30*1000))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact()
                ;
    }

    public static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new IllegalArgumentException("Token invalided");
        }
    }

    public static boolean isTokenExpired(String token){
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration().before(new Date());
    }
}
