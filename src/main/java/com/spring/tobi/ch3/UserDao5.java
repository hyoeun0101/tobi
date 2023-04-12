package com.spring.tobi.ch3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 컨텍스트를 클래스로 분리
 */
public class UserDao5 {
    private final JdbcContext jdbcContext;
    public UserDao5(JdbcContext jdbcContext ) {
        this.jdbcContext = jdbcContext;
    }
    public void deleteAll() throws Exception {
        jdbcContext.workWithStatementStrategy(
            new StatementStrategy() {
                @Override
                public PreparedStatement makeStatement(Connection c) throws SQLException {
                    return c.prepareStatement("delete from users");
                }
            }
        );
    }
    

}
