package com.spring.tobi.ch6.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 확장된 UppercaseHandler
 * 타겟이 특정 인터페이스의 구현체에 제한하지 않음. 타겟의 종류와 상관없이 적용이 가능.
 */
public class UppercaseHandler2 implements InvocationHandler {
    Object target;

    private UppercaseHandler2(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = method.invoke(target, args);
        if(ret instanceof String && method.getName().startsWith("say")) { //부가 기능 적용
            return ((String) ret).toUpperCase();
        } else {
            return ret;
        }
    }
}
