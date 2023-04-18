package com.spring.tobi.ch6.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * InvocationHandler의 구현체.
 * 위임할 타겟 객체를 주입.
 */
public class UppercaseHandler implements InvocationHandler {
    Hello target;

    public UppercaseHandler(Hello target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String ret = (String) method.invoke(target,args);// 타겟으로 위임.
        return ret.toUpperCase();// 부가 기능 제공.
    }
}
