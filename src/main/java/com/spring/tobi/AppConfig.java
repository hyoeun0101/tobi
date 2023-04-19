package com.spring.tobi;

import com.spring.tobi.ch6.proxy.MessageFactoryBean;
import com.spring.tobi.ch6.proxy.TxProxyFactoryBean;
import com.spring.tobi.ch6.proxyFactoryBean.TransactionAdvice;
import com.spring.tobi.ch6.service.UserService;
import com.spring.tobi.ch6.service.UserServiceImpl;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DriverManagerDataSource();

    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }


    /**
     * 실제 타입이 MessageFactoryBean이 아니라 Message 타입이다.
     * getObject()가 반환하는 타입으로 결정되며, 반환하는 객체가 빈이다.
     */
    @Bean
    public MessageFactoryBean message() {
        MessageFactoryBean messageFactoryBean = new MessageFactoryBean();
        messageFactoryBean.setText("Factory Bean");
        return messageFactoryBean;
    }
    @Autowired
    UserServiceImpl userServiceImpl;

    /**
     * 팩토리 빈을 사용하여 다이내믹 프록시를 빈으로 등록.
     *
     */
//    @Bean
//    public TxProxyFactoryBean userService() {
//        TxProxyFactoryBean factoryBean = new TxProxyFactoryBean();
//        // 다이내믹 프록시에게 넘겨주기 위한 타겟의 정보 필요
//        factoryBean.setTarget(userServiceImpl);
//        factoryBean.setTransactionManager(transactionManager());
//        factoryBean.setPattern("allUsers");
//        factoryBean.setServiceInterface(UserService.class);
//        return factoryBean;
//
//    }

    @Bean
    public Pointcut transactionPointcut() {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("allUsers*");
        return pointcut;
    }

    @Autowired
    TransactionAdvice transactionAdvice;
    @Bean
    public PointcutAdvisor transactionAdvisor() {
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(transactionPointcut());
        advisor.setAdvice(transactionAdvice);
        return advisor;
    }

    @Bean
    public ProxyFactoryBean userService() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(userServiceImpl);
        // 어드바이스와 어드바이저를 동시에 설정
        pfBean.setInterceptorNames("transactionAdvisor");
        return pfBean;
    }



}
