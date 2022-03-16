package com.reloadly.accountservice.service.implementation;

import com.reloadly.accountservice.dto.request.EmailRequestDto;
import com.reloadly.accountservice.dto.response.MailResponseDto;
import com.reloadly.accountservice.exceptions.CustomException;
import com.reloadly.accountservice.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final RestTemplate restTemplate;

    @Value("${mail.sender.base.url}")
    private String MAIL_SENDER_BASE_URL;


    @Override
    public MailResponseDto sendMail(EmailRequestDto emailRequestDto) {

        try {
           return restTemplate.postForObject(MAIL_SENDER_BASE_URL,emailRequestDto, MailResponseDto.class);

        } catch (Exception exp) {
            log.error("exception occurred when sending mail :: message [{}]", exp.getMessage());
            throw new CustomException("exception occurred when sending mail");
        }
    }
}
