package com.cy.jt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class JwtTests {

    private String secret = "hhhhhhh";

    @Test
    public void test (){

        Map<String,Object> map = new HashMap<>();
        map.put("username", "jack");
        map.put("permissions", "sys:res:create,sys:res:retrieve");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        Date time = calendar.getTime();

        String token =  Jwts.builder()
                            .setClaims(map)
                            //.setExpiration(new Date(System.currentTimeMillis()+30*1000))
                            .setExpiration(time)
                            .setIssuedAt(new Date())
                            .signWith(SignatureAlgorithm.HS256,secret)
                            .compact()
                            ;
        System.out.println(token);

        //
        Claims body = Jwts.parser()
                          .setSigningKey(secret)
                          .parseClaimsJws(token)
                          .getBody();
        System.out.println(body);
    }
}
