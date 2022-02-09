package com.springrest.notification.services;

import com.springrest.notification.dto.SmsRequestInput;
import com.springrest.notification.entity.Phone;
import com.springrest.notification.entity.Sms;
import com.springrest.notification.exception.GlobalExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;

public interface SmsService {
    public Sms addSms(SmsRequestInput input) throws MethodArgumentNotValidException;
    public Optional<Sms> getSmsById(String id);
    public boolean block(Phone temp);
    public boolean unblock(Phone temp);
    public List<Phone> findAll();


}
