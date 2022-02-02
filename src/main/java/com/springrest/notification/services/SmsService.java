package com.springrest.notification.services;

import com.springrest.notification.entity.Phone;
import com.springrest.notification.entity.Sms;
import com.springrest.notification.exception.GlobalExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public interface SmsService {
    public Sms addSms(Sms temp) throws MethodArgumentNotValidException;
    public Sms getSmsRequest();
    public boolean block(Phone temp);
    public List<Phone> findAll();


}
