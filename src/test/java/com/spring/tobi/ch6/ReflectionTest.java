package com.spring.tobi.ch6;

import com.spring.tobi.ch6.proxy.Hello;
import com.spring.tobi.ch6.proxy.HelloTarget;
import com.spring.tobi.ch6.proxy.UppercaseHandler;
import com.spring.tobi.ch6.proxy.HelloUppercase;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.*;


public class ReflectionTest {
    @Test
    public void invokeMethod() throws Exception {
        String name = "Spring";
        // 직접 호출
        assertThat(name.length()).isEqualTo(6);
        //리플렉션 방식으로 호출
        Method lengthMethod = String.class.getMethod("length");
        assertThat((Integer)lengthMethod.invoke(name)).isEqualTo(6);

        // 직접 호출
        assertThat(name.charAt(0)).isEqualTo('S');
        //리플렉션 방식으로 호출
        Method charAtMethod = String.class.getMethod("charAt", int.class);
        assertThat((Character)charAtMethod.invoke(name,0)).isEqualTo('S');

    }

    @Test
    public void simpleProxy() {
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("Eunoo")).isEqualTo("Hello Eunoo");
        assertThat(hello.sayHi("Eunoo")).isEqualTo("Hi Eunoo");
        assertThat(hello.sayThankYou("Eunoo")).isEqualTo("Thank you Eunoo");

    }

    @Test
    public void HelloUppercaseTest() {
        String name = "Eunoo";
        Hello proxiedHello = new HelloUppercase(new HelloTarget());
        assertThat(proxiedHello.sayHello(name)).isEqualTo("HELLO EUNOO");
        assertThat(proxiedHello.sayHi(name)).isEqualTo("HI EUNOO");
        assertThat(proxiedHello.sayThankYou(name)).isEqualTo("THANK YOU EUNOO");

    }

    @Test
    public void DynamicProxyTest() {
        String name = "Eunoo";
        // 다이내믹 프록시 생성
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),// 클래스 로더 제공
                new Class[]{Hello.class},// 구현할 인터페이스
                new UppercaseHandler(new HelloTarget()) // 부가기능, 위임 코드를 담은 InvocationHandler
        );

        assertThat(proxiedHello.sayHello(name)).isEqualTo("HELLO EUNOO");
        assertThat(proxiedHello.sayHi(name)).isEqualTo("HI EUNOO");
        assertThat(proxiedHello.sayThankYou(name)).isEqualTo("THANK YOU EUNOO");


    }
}
