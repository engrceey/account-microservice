package com.reloadly.accountservice.service;

import com.reloadly.accountservice.dto.request.EmailRequestDto;
import com.reloadly.accountservice.dto.response.MailResponseDto;

public interface MailSenderService {
    MailResponseDto sendMail(EmailRequestDto emailRequestDto);
}
