package com.spring.tobi.ch6.proxyFactoryBean;

public class Message {
    String text;

    private Message(String text) {
        this.text = text;
    }
    public String getText() {
        return this.text;
    }
    // 생성자 대신 스태틱 팩토리 메소드를 제공한다.
    public static Message newMessage(String text) {
        return new Message(text);
    }
}
