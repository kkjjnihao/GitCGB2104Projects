package sso.dao.sso.bean;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import sso.config.SpringUtil;

import java.util.Arrays;

@SpringBootTest
public class TestBean {
    @Test
    public void test (){
        ApplicationContext context = SpringUtil.getApplicationContext();
        System.out.println(context);
        System.out.println("--------------------");
        String[] strings = context.getBeanDefinitionNames();
        for (String s : strings){
            System.out.println(s);
        }
    }
}
