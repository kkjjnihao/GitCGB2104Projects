package sso.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void test (){
        Map<String, Object> map = userMapper.selectUserByUsername("admin");
        System.out.println(map);
    }

}
