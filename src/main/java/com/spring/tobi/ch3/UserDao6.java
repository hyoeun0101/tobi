package com.spring.tobi.ch3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 변하는 것, 변하지 않는 것 분리
 */
public class UserDao6 {
    private final JdbcContext jdbcContext;
    public UserDao6(JdbcContext jdbcContext ) {
        this.jdbcContext = jdbcContext;
    }
    public void deleteAll() throws Exception {
        executeSql("delete from users");
    }

    private void executeSql(final String sql) throws SQLException{
        jdbcContext.workWithStatementStrategy(
            new StatementStrategy() {

                @Override
                public PreparedStatement makeStatement(Connection c) throws SQLException {
                    return c.prepareStatement(sql);
                }
                
            }
        );
    }
    

}
