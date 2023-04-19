package com.spring.tobi.ch6.service;

import com.spring.tobi.ch5.User;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface UserService {
    void add(User user);
    void allUsersUpgradeLevel();

    List<User> getAllUser();
}
