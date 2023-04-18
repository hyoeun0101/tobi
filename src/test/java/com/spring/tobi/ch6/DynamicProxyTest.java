package com.spring.tobi.ch6;

import com.spring.tobi.ch6.proxy.Hello;
import com.spring.tobi.ch6.proxy.HelloTarget;
import com.spring.tobi.ch6.proxy.UppercaseHandler;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import static org.assertj.core.api.Assertions.*;


import java.lang.reflect.Proxy;

public class DynamicProxyTest {
    @Test
    public void simpleProxy() {
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] {Hello.class},
                new UppercaseHandler(new HelloTarget())
        );
    }

    @Test
    public void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());
        pfBean.addAdvice(new UppercaseAdvice());

        String name = "Eunoo";

        Hello prxiedHello = (Hello) pfBean.getObject();
        assertThat(prxiedHello.sayHello(name)).isEqualTo("HELLO EUNOO");
        assertThat(prxiedHello.sayHi(name)).isEqualTo("HI EUNOO");
        assertThat(prxiedHello.sayThankYou(name)).isEqualTo("THANK YOU EUNOO");
    }
    static class UppercaseAdvice implements MethodInterceptor
    {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String ret = (String) invocation.proceed();
            return ret.toUpperCase();
        }
    }

}
