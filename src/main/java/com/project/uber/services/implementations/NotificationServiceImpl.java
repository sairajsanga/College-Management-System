package com.project.uber.services.implementations;

import com.project.uber.exceptions.RuntimeConflictException;
import com.project.uber.services.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {


    private final JavaMailSender javaMailSender;

    public NotificationServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Async
    public void sendEmail(String to, String subject, String body) {
        try{
            System.out.println("Email thread: " + Thread.currentThread().getName());
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("pandeyprashantganesh@gmail.com");
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);

            javaMailSender.send(simpleMailMessage);

            log.info("Email sent successfully!");
        } catch (Exception e){
            log.error(e.getLocalizedMessage());
            throw new RuntimeConflictException(e.getLocalizedMessage());
        }

    }

    @Override
    @Async
    public void sendEmail(String[] to, String subject, String body) {
        try{
            System.out.println("Email thread: " + Thread.currentThread().getName());
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("pandeyprashantganesh@gmail.com");
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);

            javaMailSender.send(simpleMailMessage);

            log.info("Email sent successfully!");
        } catch (Exception e){
            log.error(e.getLocalizedMessage());
            throw new RuntimeConflictException(e.getLocalizedMessage());
        }
    }

}
