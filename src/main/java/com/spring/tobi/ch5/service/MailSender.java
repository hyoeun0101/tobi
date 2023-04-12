package com.spring.tobi.ch5.service;

import org.apache.logging.log4j.message.SimpleMessage;

public interface MailSender {
//    void send(SimpleMailMessage simpleMessage) throws MailException;
//    void send(SimpleMailMessage[] simpleMessages) throws MailException;
    void send(String email);
}
