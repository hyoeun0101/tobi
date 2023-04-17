package com.spring.tobi.ch6.proxy;

/**
 * 프록시 클래스. 위임할 타깃 객체를 가지고 있음.
 * 단점: 인터페이스의 모든 메서드를 구현하여 타겟 메서드로 위임해야함.
 *      Uppercase라는 부가기능을 중복 사용
 */
public class HelloUppercase implements Hello {
    // 위임할 타깃 오브젝트.
    Hello hello;

    public HelloUppercase(Hello hello) {
        this.hello = hello;
    }
    @Override
    public String sayHello(String name) {
        return hello.sayHello(name).toUpperCase();
    }

    @Override
    public String sayHi(String name) {
        return hello.sayHi(name).toUpperCase();
    }

    @Override
    public String sayThankYou(String name) {
        return hello.sayThankYou(name).toUpperCase();
    }
}
