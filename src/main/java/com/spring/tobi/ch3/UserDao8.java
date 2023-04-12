package com.spring.tobi.ch3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

/*
 * JdbcTemplate 적용하기
 */
public class UserDao8 {
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    };

    public UserDao8(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void deleteAll() throws Exception {
        this.jdbcTemplate.update("delete from users");
    }

    public void addUser(final User user) {
        String sql = "insert into users(id, name, password) values (?,?,?)";
        this.jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword());
    }

    public int getCount() {
        return this.jdbcTemplate.query(
                new PreparedStatementCreator() {

                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        return con.prepareStatement("select count(*) from users");
                    }

                },
                new ResultSetExtractor<Integer>() {

                    @Override
                    public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                        rs.next();
                        return rs.getInt(1);
                    }

                });
    }

    public User getUser(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?", userMapper);
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(
                "select * from users order by id", userMapper);
    }
}
