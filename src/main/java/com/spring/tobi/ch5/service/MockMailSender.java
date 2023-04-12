package com.spring.tobi.ch5.service;

import java.util.ArrayList;
import java.util.List;

public class MockMailSender implements MailSender{
    private List<String> requests = new ArrayList<String>();

    public List<String> getRequests() {
        return requests;
    }
    @Override
    public void send(String mail) {
        requests.add(mail);
    }
}
