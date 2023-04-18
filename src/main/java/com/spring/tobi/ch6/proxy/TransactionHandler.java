package com.spring.tobi.ch6.proxy;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TransactionHandler implements InvocationHandler {
    private Object target;
    private PlatformTransactionManager transactionManager;
    private String pattern;

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().startsWith(pattern)) {
            System.out.println("패턴 : "+ pattern);
            return invokeInTx(method, args);
        } else {
            return method.invoke(target, args);
        }

    }

    private Object invokeInTx(Method method, Object[] args) throws Throwable {
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Object ret = method.invoke(target, args);

            this.transactionManager.commit(status);
            System.out.println("커밋!");
            return ret;

        } catch (Exception e) {
            this.transactionManager.rollback(status);
            System.out.println("롤백!");
            throw e;
        }
    }
}


