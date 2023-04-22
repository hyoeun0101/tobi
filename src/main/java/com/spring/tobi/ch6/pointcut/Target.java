package com.spring.tobi.ch6.pointcut;

public class Target implements TargetInterface{

    @Override
    public void hello() {

    }

    @Override
    public void hello(String a) {

    }

    @Override
    public int minus(int a, int b) throws RuntimeException {
        return a - b;
    }

    @Override
    public int plus(int a, int b) {
        return a + b;
    }

    public void method(){}
}
