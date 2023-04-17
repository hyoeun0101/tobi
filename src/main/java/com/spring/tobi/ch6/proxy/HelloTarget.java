package com.spring.tobi.ch6.proxy;

/**
 * 타겟 클래스.
 * 출력 기능을 함.(핵심 기능)
 */
public class HelloTarget implements Hello {
    @Override
    public String sayHello(String name) {
        return "Hello "+ name;
    }

    @Override
    public String sayHi(String name) {
        return "Hi "+ name;
    }

    @Override
    public String sayThankYou(String name) {
        return "Thank you " + name;
    }
}
