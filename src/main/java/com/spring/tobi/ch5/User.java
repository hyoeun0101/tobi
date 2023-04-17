package com.spring.tobi.ch5;

public class User {
    private String id;
    private String name;
    private String password;
    private int level;
    private int login;
    private int recommend;
    private String email;

    public User() {}
    public User(String id, String name, String password, int level, int login, int recommend, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
        this.email = email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return this.level;
    }

    public Level getToEnumLevel() {
        return Level.valueToLevel(this.level);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", level=" + level +
                ", login=" + login +
                ", recommend=" + recommend +
                ", email='" + email + '\'' +
                '}';
    }

    public void upgradeLevel() {
        Level nextLevel = Level.valueToLevel(this.level).getNextLevel();

        if(nextLevel != null) {
            this.level = Level.valueToInt(nextLevel);
        } else {
            throw new IllegalStateException(this.level + "은 업그레이드 불가능");
        }
    }
}
