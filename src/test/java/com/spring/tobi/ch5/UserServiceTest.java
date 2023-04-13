package com.spring.tobi.ch5;

import com.spring.tobi.ch5.dao.UserDao;
import com.spring.tobi.ch5.service.MailSender;
import com.spring.tobi.ch5.service.MockMailSender;
import com.spring.tobi.ch5.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    MailSender mailSender;

    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    UserDao userDao;

    @Test
    public void upgradeAllOrNothing() throws Exception{
        //given
        User user = new User("testId","testName","testPwd",2, 40,1,"testEmail@test.com");
        //when
        UserService userService1 = new UserService(transactionManager, userDao, mailSender);
        userService1.upgradeLevel(user);

        //then
        // 업그레이드 및 이메일 전송 완료
        assertThat(user.getLevel()).isEqualTo(3);
    }

    @Test
    @DirtiesContext
    public void upgradeLevels() {
        //given
        MockMailSender mockMailSender = new MockMailSender();
        UserService userService = new UserService(transactionManager, userDao, mockMailSender);
        User user = new User("testId","testName","testPwd",2, 40,1,"testEmail@test.com");

        //when
        userService.upgradeLevel(user);

        List<String> requests = mockMailSender.getRequests();
        assertThat(user.getEmail()).isEqualTo(requests.get(0));
    }
}
