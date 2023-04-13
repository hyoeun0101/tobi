package com.spring.tobi.ch6.dao;


import com.spring.tobi.ch5.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDaoJdbc implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(rs.getInt("level"));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
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


    @Override
    public void update(User user) {
        this.jdbcTemplate.update("update users set level = ? where id=? ",user.getLevel(), user.getId());
    }

}
