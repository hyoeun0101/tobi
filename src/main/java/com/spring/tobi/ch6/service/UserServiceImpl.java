package com.spring.tobi.ch6.service;

import com.spring.tobi.ch5.User;
import com.spring.tobi.ch6.dao.UserDao;
import com.spring.tobi.ch5.service.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private  UserDao userDao;
    private MailSender mailSender;


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void add(User user) {
        userDao.add(user);

    }

    /**
     * 트랜잭션 경계설정은 UserServiceTx로 분리하기.
     */
    @Override
    public void allUsersUpgradeLevel(){
        List<User> users = userDao.getAll();
        for(User user: users) {
            if(checkEnableLevelUp(user)) {
                upgradeLevel(user);

            }
        }
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAll();
    }

    private boolean checkEnableLevelUp(User user) {
        switch (user.getLevel()) {
            case 1: return user.getLogin() >= 50;
            case 2: return user.getLogin() >= 30;
            case 3: return false;
            default: throw new IllegalArgumentException("Unknown Level : "+ user.getLevel());
        }
    }


    /**
     * 레벨 업그레이드 시 이메일 전송하기
     * @param user
     */
    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeMail(user);
    }

/**
 * 메일 서버에게 전송하는 코드
 * 1. 추상화가 필요하다.
 */
//    private void sendUpgradeMail(User user) {
//        //메일 전송 기능
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "mail.ksug.org");
//        Seesion s = Session.getInstance(props, null);
//        MimeMessage message = new MimeMessage(s);
//        try {
//            message.setFrom(new InternetAddress("이메일"));
//            message.addRecipient(보낼 이메일);
//            message.setSubject("제목");
//            message.setText("내용");
//
//            Transport.send(message);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * 2. 메일 발송 기능 추상화하기
     * 2-1.직접 구현체 생성 - 추상화했지만 DI 받지 않고 있다.
     */
//    private void sendUpgradeMail(User user) {

//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl(); //MailSender의 구현체 직접 생성
//        mailSender.sendHost("mail.server.com");
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(user.getEmail());
//        mailMessage.setFrom("from 이메일");
//        mailMessage.setSubject("제목");
//        mailMessage.setText("내용");
//
//        mailSender.send(mailMessage);
//
//    }

    /**
     * 2-2. 직접 구현체 생성하지 않고, 인터페이스를 통해 DI 받기.
     * - 이를 통해 테스트 시 Mock Object를 DI할 수 있게 되었다.
     */
    private void sendUpgradeMail(User user) {
        // 실제 코드 생략
//        SimpelMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(user.getEmail());
//        mailMessage.setFrom("from 이메일");
//        mailMessage.setSubject("제목");
//        mailMessage.setText("내용");
//        this.mailSender.send(mailMessage);
        this.mailSender.send(user.getEmail());

    }

}
