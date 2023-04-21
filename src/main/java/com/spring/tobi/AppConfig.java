package com.spring.tobi;

import com.spring.tobi.ch6.proxyFactoryBean.MessageFactoryBean;
import com.spring.tobi.ch6.proxyFactoryBean.NameMatchClassMethodPointcut;
import com.spring.tobi.ch6.proxyFactoryBean.TransactionAdvice;
import com.spring.tobi.ch6.service.UserServiceImpl;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
     * 대신 스프링에서 지원하는 ProxyFactoryBean을 사용하자.
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


    /**
     * 어드바이스 적용할 메소드 이름 패턴 지정 역할만 가진 포인트컷.
     */
//    @Bean
//    public Pointcut transactionPointcut() {
//        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
//        pointcut.setMappedName("allUser*");
//        return pointcut;
//    }

    @Autowired
    TransactionAdvice transactionAdvice;
    @Bean
    public PointcutAdvisor transactionAdvisor() {
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(transactionPointcut());

        transactionAdvice.setTransactionManager(transactionManager());
        advisor.setAdvice(transactionAdvice);
        return advisor;
    }

    /**
     * ProxyFactoryBean이 다이내믹 프록시를 생성해준다.
     * DefaultAdvisorAutoProxyCreator를 빈으로 등록하면 자동으로 프록시 생성하기 때문에
     * 필요없다!
     */
    @Bean
    public ProxyFactoryBean userService() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(userServiceImpl);
        // 어드바이스와 어드바이저를 동시에 설정
        pfBean.setInterceptorNames("transactionAdvisor");
        return pfBean;
    }

    /**
     * 빈 전처리기.
     * 모든 빈에 포인트컷을 적용하고, 해당하면 프록시를 만들어 원래 빈 오브젝트와 바꿔치기한다.
     * 빈 오브젝트는 프록시를 통해서만 접근 할 수 있다.
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    /**
     * 클래스 이름, 메소드 이름 패턴 적용한 포인트컷
     */
    @Bean
    public Pointcut transactionPointcut() {
        NameMatchClassMethodPointcut pointcut = new NameMatchClassMethodPointcut();
        pointcut.setMappedClassName("UserService*"); // 프록시를 생성할 클래스 이름 패턴
        pointcut.setMappedName("*"); // 어드바이스를 적용할 메소드 이름 패턴
        return pointcut;
    }





}
