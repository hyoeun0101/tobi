package com.spring.tobi.ch6;

import com.spring.tobi.ch5.User;

import java.util.ArrayList;
import java.util.List;

public class TestUser {
    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User("id1","name1","pwd1",2,11,0,"email1"));
        users.add(new User("id2","name2","pwd2",1,51,2,"email2"));// 업데이트
        users.add(new User("id3","name3","pwd3",3,111,0,"email3"));
        users.add(new User("id4","name4","pwd4",1,5,0,"email4"));
        users.add(new User("id5","name5","pwd5",2,31,0,"email5")); //업데이트
    }

    public static List<User> getUsers() {
        return TestUser.users;
    }
}
