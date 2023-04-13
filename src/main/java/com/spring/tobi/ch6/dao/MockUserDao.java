package com.spring.tobi.ch6.dao;

import com.spring.tobi.ch5.User;

import java.util.ArrayList;
import java.util.List;

public class MockUserDao implements UserDao{
    private List<User> users = new ArrayList<>();
    private List<User> updatedUsers = new ArrayList<>();

    public MockUserDao(List<User> users) {
        this.users = users;
    }

    public List<User> getUpdatedUsers() {
        return this.updatedUsers;
    }

    public List<User> getAllUsers() {
        return this.users;
    }


    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void add(User user) {

        throw new UnsupportedOperationException();

    }

    @Override
    public User getUser(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> getAll() {
        return this.users;
    }

    @Override
    public void update(User user) {
        this.updatedUsers.add(user);

    }
}
