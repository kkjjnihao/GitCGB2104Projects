package com.jt.sso.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**基于Jwt规范创建和解析token的工具类*/
public class JwtUtils {
    private static String secret="AAABBBCCCDDDEEE";
    /**基于负载和算法创建token信息*/
    public static String generatorToken(Map<String,Object> map){
       return Jwts.builder()
                //设置自定义负载信息(存储用户的认证和授权信息)
                .setClaims(map) //map中存储用户的认证和授权信息
                //设置令牌有效时长
                .setExpiration(new Date(System.currentTimeMillis()+30*60*1000))
                //设置令牌签发时间
                //.setIssuedAt(new Date())//
                //设置签名的加密密钥和算法
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();//签约,创建token
    }
    /**解析token获取数据*/
    public static Claims getClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    /**判定token是否失效*/
    public static boolean isTokenExpired(String token){
       Date expiration=getClaimsFromToken(token).getExpiration();
       return expiration.before(new Date());
    }
    //.....

}
