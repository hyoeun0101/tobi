package com.spring.tobi.ch5.dao;


import com.spring.tobi.ch5.User;

import java.util.List;

public interface UserDao {

    void deleteAll();

    void add(User user);

    User getUser(String id);

    List<User> getAll();

    void update(User user);

}