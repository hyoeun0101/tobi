package com.spring.tobi.ch3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;
/**
 * 전략을 익명 내부 클래스로 구현
 */
public class UserDao4 {
    private final DataSource dataSource;

    public UserDao4(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void deleteAll() throws SQLException{
        jdbcMakeStatement(
            new StatementStrategy() {
                @Override
                public PreparedStatement makeStatement(Connection c) throws SQLException {
                    return c.prepareStatement("delete from users");
                }

            }
        );
    }

    private void jdbcMakeStatement(StatementStrategy strategy) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try{
            c = dataSource.getConnection();
            ps = strategy.makeStatement(c);
        } catch (SQLException e) {
            throw e;
        } finally {
            if(ps != null) { try{ps.close();} catch(SQLException e){}}
            if(c != null) { try{c.close();} catch(SQLException e){}}
        }   
    }
}
