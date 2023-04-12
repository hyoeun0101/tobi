package com.spring.tobi.ch3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 템플릿 메소드 패턴 적용
 */
public abstract class UserDao2 {
    private final DataSource dataSource;

    public UserDao2(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void deleteAll() throws SQLException{
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();

            ps = makStatement(c);
        } catch (SQLException e) {
            throw e;
        } finally {
            if(ps != null) { try{ps.close();} catch(SQLException e){}}
            if(c != null) { try{c.close();} catch(SQLException e){}}
        }
    }

    abstract protected PreparedStatement makStatement(Connection c) throws SQLException;
    
}
