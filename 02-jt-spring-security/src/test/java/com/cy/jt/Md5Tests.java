package com.cy.jt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
public class Md5Tests {
    @Test
    void testMd5Enode(){
        String password = "123456";
        String salt = "www.tedu.cn";
        String pwd = DigestUtils.md5DigestAsHex((password+salt).getBytes());
        System.out.println(pwd);
        //第一次 1f6d4c93751108d06b6759c2bc353a34
        //第二次 1f6d4c93751108d06b6759c2bc353a34
        System.out.println(pwd.length());
    }
}
