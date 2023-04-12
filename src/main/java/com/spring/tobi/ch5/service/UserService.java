package com.spring.tobi.ch5.service;

import com.spring.tobi.ch5.Level;
import com.spring.tobi.ch5.User;
import com.spring.tobi.ch5.dao.UserDao;
import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;

import com.spring.tobi.ch3.UserDao8;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ws.mime.MimeMessage;
import org.springframework.ws.soap.addressing.AddressingException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PlatformTransactionManager transactionManager;
    private final UserDao userDao;

    private final MailSender mailSender;

    // 레벨 업그레이드 시 메일 전송
    public void upgradeLevels(User user) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            List<User> users = userDao.getAll();
            for(User u: users) {
                if(checkEnableLevelUp(u)) {
                    upgradeLevel(user);
                }
            }
            transactionManager.commit(status);
        } catch(RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    private boolean checkEnableLevelUp(User user) {
        switch (user.getLevel()) {
            case BASIC: return user.getLogin() >= 50;
            case SILVER: return user.getLogin() >= 30;
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level : "+ user.getLevel());
        }
    }


    /**
     * 레벨 업그레이드 시 이메일 전송하기
     * @param user
     */
    public void upgradeLevel(User user) {
        user.upgradeLevel();
//        userDao.update(user);
        sendUpgradeMail(user);
    }

/**
 * 메일 서버에게 전송하는 코드
 * 하지만 테스트 시에는 직접 메일 서버에게 계속 이메일을 보낼 필요없다.
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
     * 메일 발송 기능 추상화하기
     * : JavaMail API를 사용하지 않고, 메일 발송 기능 코드를 테스트하기
     * 1. 직접 구현체 생성
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
     * 2. 직접 구현체 생성하지 않고, 인터페이스를 통해 DI 받기.
     */
    private void sendUpgradeMail(User user) {
//        SimpelMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(user.getEmail());
//        mailMessage.setFrom("from 이메일");
//        mailMessage.setSubject("제목");
//        mailMessage.setText("내용");

        //this.mailSender.send(mailMessage);
        this.mailSender.send(user.getEmail());

    }

}
