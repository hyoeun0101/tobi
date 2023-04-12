package com.spring.tobi.ch4;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
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

    @Override
    public void deleteAll() throws DataAccessException {
        this.jdbcTemplate.update("delete from users");
    }

    @Override
    public void add(User user) {
        String sql = "insert into users(id, name, password) values (?,?,?)";
        this.jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword());

    }

    @Override
    public User getUser(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?", userMapper);

    }

    @Override
    public List<User> getAll() throws DuplicateKeyException {
        return this.jdbcTemplate.query(
                "select * from users order by id", userMapper);
    }

}
