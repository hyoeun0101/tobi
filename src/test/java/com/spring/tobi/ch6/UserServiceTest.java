package com.spring.tobi.ch6;

import com.spring.tobi.ch5.User;
import com.spring.tobi.ch5.service.MockMailSender;
import com.spring.tobi.ch6.dao.MockUserDao;
import com.spring.tobi.ch6.dao.UserDao;
import com.spring.tobi.ch5.service.MailSender;
import com.spring.tobi.ch6.service.UserServiceImpl;
import com.spring.tobi.ch6.service.UserServiceTx;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    UserDao userDao;


    @Autowired
    PlatformTransactionManager transactionManager;


    @Test
    public void upgradeAllOrNothing() throws Exception {
        //given
        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);
        userServiceImpl.setUserDao(userDao);

        UserServiceTx userServiceTx = new UserServiceTx();
        userServiceTx.setTransactionManager(transactionManager);
        userServiceTx.setUserService(userServiceImpl);

        //when
        List<User> users = userServiceImpl.getAllUser();
        for(User user: users) {
            System.out.println(user.toString());
        }
        userServiceTx.allUsersUpgradeLevel();

        //then
    }


    @Test
    public void allUsersUpgradeLevelTest() {
        //given
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        MockUserDao mockUserDao = new MockUserDao(TestUser.getUsers());
        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setUserDao(mockUserDao);
        userServiceImpl.setMailSender(mockMailSender);

        //when
        userServiceImpl.allUsersUpgradeLevel();

        //then
        List<User> updatedUsers = mockUserDao.getUpdatedUsers();
        assertThat(updatedUsers.size()).isEqualTo(2);
        checkUserAndLevel(updatedUsers.get(0),"id2",2);
        checkUserAndLevel(updatedUsers.get(1), "id5", 3);
    }

    private void checkUserAndLevel(User updatedUser, String expectedUserId, int expectedLevel) {
        assertThat(updatedUser.getId()).isEqualTo(expectedUserId);
        assertThat(updatedUser.getLevel()).isEqualTo(expectedLevel);
    }
}
