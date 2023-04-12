package com.spring.tobi.ch5.service;

import org.springframework.stereotype.Service;

/**
 * 인터페이스를 구현했을 뿐, 하는 일은 없다.
 * 테스트 시 사용한다.
 * (실제 메일 전송 시에는 JavaMailSenderImpl 구현체를 사용.)
 */
@Service
public class DummyMailSender implements MailSender{
    @Override
    public void send(String mail) {
        System.out.println(mail + " : 이메일 전송 완료!");

    }
}
