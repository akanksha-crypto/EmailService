package com.example.emailservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailDto {
    private String to;
    private String subject;
    private String body;
}
