package com.cy.jt.security;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTests {

    @Test
    public void test (){

        Map<String,Object> map = new HashMap<>();
        map.put("username", "jack");
        map.put("permissions", "sys:res:create");

        String token =  Jwts.builder()
                            .setSubject("jwt")
                            .setClaims(map)
                            .setExpiration(new Date(System.currentTimeMillis()+30*1000))
                            .setIssuedAt(new Date())
                            .compact()
                            ;
        System.out.println(token);
    }
}
