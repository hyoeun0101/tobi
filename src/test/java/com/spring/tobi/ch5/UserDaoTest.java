package com.spring.tobi.ch5;

import com.spring.tobi.ch5.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(properties = "")
public class UserDaoTest {
    @Autowired
    UserDao userDao;
    @Autowired
    DataSource dataSource;

    @Test
    public void getAllUserTest() throws Exception {
        System.out.println(dataSource);
        System.out.println(userDao);
        List<User> all = userDao.getAll();
        for(User user: all) {
            System.out.println(user.toString());
        }
    }
}
