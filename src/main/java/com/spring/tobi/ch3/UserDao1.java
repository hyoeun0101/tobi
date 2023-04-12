package com.spring.tobi.ch3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class UserDao1 {
    private final DataSource dataSource;

    public UserDao1(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void deleteAll() throws SQLException{
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();

            ps = c.prepareStatement("delete from users");
        } catch (SQLException e) {
            throw e;
        } finally {
            if(ps != null) { try{ps.close();} catch(SQLException e){}}
            if(c != null) { try{c.close();} catch(SQLException e){}}
        }
    }
    
}
