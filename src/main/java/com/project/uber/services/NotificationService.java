package com.project.uber.services;

public interface NotificationService {

    void sendEmail(String to,String subject,String body);

    void sendEmail(String[] to,String subject,String body);
}
