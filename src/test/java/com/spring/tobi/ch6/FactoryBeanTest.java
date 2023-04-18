package com.spring.tobi.ch6;

import com.spring.tobi.ch6.proxy.Message;
import com.spring.tobi.ch6.proxy.MessageFactoryBean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class FactoryBeanTest {

    @Autowired
    ApplicationContext context;


    @Test
    @DisplayName("빈으로 등록된 MessageFactoryBean이 Message 타입인지 확인하기")
    public void getMessageFromFactoryBean() {
        Object message = context.getBean("message");
        assertThat(message.getClass()).isEqualTo(Message.class);
        assertThat(((Message)message).getText()).isEqualTo("Factory Bean");
    }

    @Test
    @DisplayName("&기호를 붙여주면 FactoryBean 자체를 반환한다.")
    public void returnFactoryBean() {
        Object factory = context.getBean("&message");
        assertThat(factory.getClass()).isEqualTo(MessageFactoryBean.class);
    }
}
