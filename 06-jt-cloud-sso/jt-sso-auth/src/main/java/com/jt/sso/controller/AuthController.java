package com.jt.sso.controller;

import com.jt.sso.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AuthController {
    @GetMapping("/auth/info")
    public Map<String,Object> getAuthentication(String token){
        System.out.println("token==="+token);
        Claims claims=JwtUtils.getClaimsFromToken(token);
        boolean flag=claims.getExpiration().before(new Date());
        String username=(String)claims.get("username");
        List<String> list=(List<String>)
                claims.get("authorities");
        Map<String,Object> map=new HashMap<>();
        map.put("expired",flag);
        map.put("username",username);
        map.put("authorities",list);
        return map;
    }
}
