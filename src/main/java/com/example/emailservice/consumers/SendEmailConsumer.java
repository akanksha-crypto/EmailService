package com.example.emailservice.consumers;

import com.example.emailservice.dtos.SendEmailDto;
import com.example.emailservice.util.EmailUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
@Service
public class SendEmailConsumer {
    private ObjectMapper objectMapper;

    public SendEmailConsumer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "sendEmail", groupId = "emailService")
    public void handleSendEmailEventInKafka(String message) throws JsonProcessingException {
        SendEmailDto sendEmailDto = objectMapper.readValue(
                message,
                SendEmailDto.class);

        //if we want to send email over we need SMTP and for that we need to add the properties
        //which java provides us
        // We also need Email util which we will add over here.

        String to = sendEmailDto.getTo();
        String subject = sendEmailDto.getSubject();
        String body = sendEmailDto.getBody();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("makanksha011@gmail.com",
                        "qqdohaamlsfmuldr");
            }
        };
        Session session = Session.getInstance(props, auth);
        EmailUtil.sendEmail(session,to,subject,body);
    }
}
