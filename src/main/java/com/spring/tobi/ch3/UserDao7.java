package com.spring.tobi.ch3;

import java.sql.SQLException;

/*
 * 재사용 가능한 메서드는 템플릿 클래스로 옮기기.
 */
public class UserDao7 {
    private final JdbcContext jdbcContext;

    public UserDao7(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void deleteAll() throws SQLException {
        jdbcContext.executeSql("delete from users");
    }

}
