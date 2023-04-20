package com.spring.tobi.ch6;

import com.spring.tobi.ch6.proxy.Hello;
import com.spring.tobi.ch6.proxy.HelloTarget;
import com.spring.tobi.ch6.proxy.UppercaseHandler;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import static org.assertj.core.api.Assertions.*;


import java.lang.reflect.Proxy;

public class DynamicProxyTest {
    @Test
    @DisplayName("1. JDK 다이내믹 프록시 생성")
    public void simpleProxy() {
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] {Hello.class},
                new UppercaseHandler(new HelloTarget())
        );
    }

    @Test
    @DisplayName("2. ProxyFactoryBean 생성")
    public void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());//타깃 설정
        pfBean.addAdvice(new UppercaseAdvice());//부가 기능을 담은 Advice 추가

        // 팩토리 빈이므로 getObject로 프록시 반환
        Hello prxiedHello = (Hello) pfBean.getObject();
        assertThat(prxiedHello.sayHello("Eunoo")).isEqualTo("HELLO EUNOO");
        assertThat(prxiedHello.sayHi("Eunoo")).isEqualTo("HI EUNOO");
        assertThat(prxiedHello.sayThankYou("Eunoo")).isEqualTo("THANK YOU EUNOO");
    }
    static class UppercaseAdvice implements MethodInterceptor
    {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String ret = (String) invocation.proceed();
            return ret.toUpperCase();
        }
    }


    @Test
    @DisplayName("포인트컷까지 적용한 ProxyFactoryBean")
    public void pointcutAdvisor() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());
        // 메소드 이름으로 대상 선정하는 포인트컷
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        // 포인트컷과 어드바이스 한 번에 추가
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

        Hello proxiedHello = (Hello) pfBean.getObject();

        assertThat(proxiedHello.sayHello("Eunoo")).isEqualTo("HELLO EUNOO");
        assertThat(proxiedHello.sayHi("Eunoo")).isEqualTo("HI EUNOO");
        // 포인트컷 조건과 부합
        assertThat(proxiedHello.sayThankYou("Eunoo")).isEqualTo("Thank you Eunoo");

    }

    @Test
    @DisplayName("포인트컷의 역할은 두가지이다. 프록시를 적용할 클래스인가, 어드바이스를 적용할 메소드인가")
    public void classNamePointcutAdvisor() {
        NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut() {
            // 프록시를 적용할 클래스인지 확인
            @Override
            public ClassFilter getClassFilter() {
                return new ClassFilter() {
                    @Override
                    public boolean matches(Class<?> clazz) {
                        return clazz.getSimpleName().startsWith("HelloT");
                    }
                };
            }
        };

        classMethodPointcut.setMappedName("sayH*");

        checkAdviced(new HelloTarget(), classMethodPointcut, true);

        class HelloWorld extends HelloTarget{};
        checkAdviced(new HelloWorld(), classMethodPointcut, false);

        class HelloToby extends HelloTarget{};
        checkAdviced(new HelloToby(), classMethodPointcut, true);
    }

    private void checkAdviced(Object target, Pointcut pointcut, boolean adviced) {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(target);
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
        Hello proxiedHello = (Hello) pfBean.getObject();

        if(adviced) { ///적용 대상인가
            assertThat(proxiedHello.sayHello("Eunoo")).isEqualTo("HELLO EUNOO");
            assertThat(proxiedHello.sayHi("Eunoo")).isEqualTo("HI EUNOO");
            assertThat(proxiedHello.sayThankYou("Eunoo")).isEqualTo("Thank you Eunoo");
        } else {
            assertThat(proxiedHello.sayHello("Eunoo")).isEqualTo("Hello Eunoo");
            assertThat(proxiedHello.sayHi("Eunoo")).isEqualTo("Hi Eunoo");
            assertThat(proxiedHello.sayThankYou("Eunoo")).isEqualTo("Thank you Eunoo");
        }
    }

}
