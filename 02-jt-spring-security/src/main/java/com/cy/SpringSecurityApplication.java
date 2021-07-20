package com.cy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
        encodePwd();
    }
    static void encodePwd(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "123456"; //明文
        String pwd = encoder.encode("123456");
        System.out.println(pwd);
    }
}
