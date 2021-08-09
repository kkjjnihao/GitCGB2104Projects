package sso.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class DataSourceTests {
    @Autowired
    private DataSource dataSource;
    @Test
    public void test () throws SQLException {
        Connection conn = dataSource.getConnection();
        System.out.println("----------------------");
        System.out.println(conn);
        System.out.println("----------------------");
    }
}
