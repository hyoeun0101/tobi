package com.spring.tobi.ch3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;
/**
 * 전략 패턴 적용
 */
public class UserDao3 {
    private final DataSource dataSource;

    public UserDao3(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void deleteAll() throws SQLException{
        StatementStrategy strategy = new DeleteAllStatement();
        jdbcMakeStatement(strategy);
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
