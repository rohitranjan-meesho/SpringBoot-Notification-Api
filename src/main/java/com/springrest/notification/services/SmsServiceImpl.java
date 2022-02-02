package com.springrest.notification.services;

import com.springrest.notification.dao.PhoneDao;
import com.springrest.notification.dao.SmsDao;
import com.springrest.notification.entity.Phone;
import com.springrest.notification.entity.Sms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsServiceImpl implements SmsService{

    @Autowired
    private SmsDao smsDao;

    @Autowired
    private PhoneDao phoneDao;

    @Override
    public Sms addSms(Sms temp) {
        smsDao.save(temp);
        return temp;

    }

    @Override
    public Sms getSmsRequest() {
        return null;
    }

    @Override
    public boolean block(Phone temp) {
        boolean result=phoneDao.save(temp);
        return result;
    }

    @Override
    public List<Phone> findAll() {
        return phoneDao.findAll();
    }
}
