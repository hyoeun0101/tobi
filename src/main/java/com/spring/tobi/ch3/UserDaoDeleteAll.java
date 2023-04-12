package com.spring.tobi.ch3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class UserDaoDeleteAll extends UserDao2{

    public UserDaoDeleteAll(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected PreparedStatement makStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("delete from users");
        return ps;
    }
    
}
