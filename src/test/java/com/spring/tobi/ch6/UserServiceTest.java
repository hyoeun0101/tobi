package com.spring.tobi.ch6;

import com.spring.tobi.ch5.Level;
import com.spring.tobi.ch5.User;
import com.spring.tobi.ch5.service.MockMailSender;
import com.spring.tobi.ch6.dao.MockUserDao;
import com.spring.tobi.ch6.dao.UserDao;
import com.spring.tobi.ch5.service.MailSender;
import com.spring.tobi.ch6.proxy.TransactionHandler;
import com.spring.tobi.ch6.proxy.TxProxyFactoryBean;
import com.spring.tobi.ch6.service.UserService;
import com.spring.tobi.ch6.service.UserServiceImpl;
import com.spring.tobi.ch6.service.UserServiceTx;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.PlatformTransactionManager;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserDao userDao;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    ApplicationContext context;

    List<User> users = new ArrayList<User>();


    @Test
    public void upgradeAllOrNothing() throws Exception {
        //given
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);
        userServiceImpl.setUserDao(userDao);

        UserServiceTx userServiceTx = new UserServiceTx();
        userServiceTx.setTransactionManager(transactionManager);
        userServiceTx.setUserService(userServiceImpl);

        //when
        userServiceTx.allUsersUpgradeLevel();

        //then
    }


    @Test
    public void allUsersUpgradeLevelTest() throws Exception{
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


    @Test
    public void mockAllUserUpgradeLevelTest() throws Exception {
        //given
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        //1. 인터페이스를 이용해 mock 객체를 만든다.
        UserDao mockUserDao = mock(UserDao.class);
        //2. 리턴값을 지정해준다.
        // getAll() 메서드를 호출하면 users가 리턴된다.
        when(mockUserDao.getAll()).thenReturn(this.users);
        //3. mock 객체를 DI한다.
        userServiceImpl.setUserDao(mockUserDao);

        MailSender mockMailSender = mock(MailSender.class);
        userServiceImpl.setMailSender(mockMailSender);

        //when
        userServiceImpl.allUsersUpgradeLevel();

        //then
        //4. 특정 메소드가 호출됐는지, 어떤 값을 가지고 몇 번 호출됐는지 검증한다.
        // UserDao의 update메서드가 2번 호출됐는지 확인. any()는 파라미터 상관없이 호출 횟수만 확인.
        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao, times(2)).update(any(User.class));
        // update()메소드의 파라미터를 검증.
        verify(mockUserDao).update(this.users.get(1));
        assertThat(this.users.get(1).getLevel()).isEqualTo(Level.valueToInt(Level.SILVER));
        verify(mockUserDao).update(this.users.get(4));
        assertThat(this.users.get(4).getLevel()).isEqualTo(Level.valueToInt(Level.GOLD));
    }


    @Test
    @DisplayName("UserServiceImpl을 타겟으로 한 다이내믹 프록시를 직접 생성")
    public void TransactionHandlerTest() throws Exception {
        //타겟(UserServiceImpl) 생성
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);
        MockUserDao mockUserDao = new MockUserDao(TestUser.getUsers());
        userServiceImpl.setUserDao(mockUserDao);

        // 핸들러 생성 및 DI.
        TransactionHandler txHandler = new TransactionHandler();
        txHandler.setTarget(userServiceImpl);
        txHandler.setTransactionManager(this.transactionManager);
        txHandler.setPattern("allUsers"); // 해당 패턴 가진 메서드만 실행.
        // 다이내믹 프록시 생성
        UserService txUserService = (UserService) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{UserService.class},
                txHandler
        );


        //when
        assertThat(TestUser.getUsers().get(1).getLevel()).isEqualTo(1);
        try {
            txUserService.allUsersUpgradeLevel();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //then
            List<User> updatedUsers = mockUserDao.getUpdatedUsers();
            assertThat(updatedUsers.size()).isEqualTo(2);
            checkUserAndLevel(updatedUsers.get(0),"id2",2);
            checkUserAndLevel(updatedUsers.get(1), "id5", 3);


        }
    }
    @Test
    @DisplayName("다이내믹 프록시를 팩토리 빈을 통해 빈으로 등록한 후 사용")
    @DirtiesContext
    public void upgradeAllOrNothing2() throws Exception {
        //타겟(UserServiceImpl) 생성
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);
        MockUserDao mockUserDao = new MockUserDao(TestUser.getUsers());
        userServiceImpl.setUserDao(mockUserDao);

        // 팩토리 빈 자체 가져와서 getObject를 호출하여 다이내믹 프록시 반환.
        TxProxyFactoryBean txProxyFactoryBean = context.getBean("userService", TxProxyFactoryBean.class);
        txProxyFactoryBean.setTarget(userServiceImpl);
        UserService txUserService = (UserService) txProxyFactoryBean.getObject();

    }


}
