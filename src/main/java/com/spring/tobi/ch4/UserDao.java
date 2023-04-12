package com.spring.tobi.ch4;

import java.util.List;

public interface UserDao {

    void deleteAll();

    void add(User user);

    User getUser(String id);

    List<User> getAll();

}