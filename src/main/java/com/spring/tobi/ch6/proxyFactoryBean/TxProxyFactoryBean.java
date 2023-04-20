package com.spring.tobi.ch6.proxyFactoryBean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

public class TxProxyFactoryBean implements FactoryBean<Object> {
    Object target;
    PlatformTransactionManager transactionManager;
    String pattern;
    Class<?> serviceInterface;

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    /**
     * 다이내믹 프록시를 생성, 반환 타입은 타겟의 인터페이스(UserService)이다.
     */
    @Override
    public Object getObject() throws Exception {
        TransactionHandler txHandler = new TransactionHandler();
        txHandler.setTarget(this.target);
        txHandler.setTransactionManager(this.transactionManager);
        txHandler.setPattern(this.pattern);
        return Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class[]{serviceInterface}, txHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return this.serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
