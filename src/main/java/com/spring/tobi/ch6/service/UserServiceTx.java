package com.spring.tobi.ch6.service;

import com.spring.tobi.ch5.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

//@Service
public class UserServiceTx implements UserService{
    UserService userService;
    PlatformTransactionManager transactionManager;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void add(User user) {
        //메서드 구현과 위임
        userService.add(user);
    }

    @Override
    public void allUsersUpgradeLevel() throws Exception{
        // 메서드 구현, 부가기능 추가
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            userService.allUsersUpgradeLevel();
//            if(true) throw new RuntimeException("임의의 예외 발생");

            transactionManager.commit(status);
        } catch(RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    @Override
    public List<User> getAllUser() {
        return userService.getAllUser();
    }


}
