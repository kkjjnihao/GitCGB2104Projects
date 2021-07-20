package com.cy.jt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class BrcyptTests {

    @Test
    void testEncode(){
     String password = "123456";
     BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
     String newPwd = encoder.encode(password);
        System.out.println(newPwd);
        System.out.println(newPwd.length());
        boolean flag = encoder.matches(password, newPwd);
        System.out.println("flag = " + flag);
    }
}
