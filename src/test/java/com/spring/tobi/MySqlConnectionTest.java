package com.spring.tobi;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MySqlConnectionTest {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/tobi?useUnicode=true&characterEncoding=utf8";

    private static final String USER = "eun";
    private static final String PW = "1234";

    @Autowired
    DataSource dataSource;

    @Test
    public void testConnectionDriverManager() throws Exception {
        Class.forName(DRIVER);
        try (Connection c = DriverManager.getConnection(URL, USER, PW)) {
            System.out.println(c);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testConnection() {
        System.out.println(dataSource);
    }
}
