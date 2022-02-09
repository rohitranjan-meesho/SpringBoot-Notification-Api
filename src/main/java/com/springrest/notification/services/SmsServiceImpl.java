package com.springrest.notification.services;

import com.springrest.notification.dao.PhoneDao;
import com.springrest.notification.dao.SmsDao;
import com.springrest.notification.dto.SendSmsKafkaRequest;
import com.springrest.notification.dto.SmsRequestInput;
import com.springrest.notification.entity.Phone;
import com.springrest.notification.entity.Sms;
import com.springrest.notification.exception.GlobalExceptionHandler;
import org.apache.kafka.common.errors.ApiException;
import org.apache.kafka.common.network.Send;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SmsServiceImpl implements SmsService{

    @Autowired
    private SmsDao smsDao;

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private KafkaTemplate<String, SendSmsKafkaRequest> kafkaTemplate;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private RedisTemplate template;


    private static final String topic="mytopic";

    @Override
    public Sms addSms(SmsRequestInput input) {

        if (template.hasKey(input.getPhone_number()))
        {
            //throw new MethodArgumentNotValidException();
            System.out.println("number in blocked list!");
            return null;
        }

        Sms temp=Sms.builder()
                .phone_number(input.getPhone_number())
                .message(input.getMessage())
                .status("IN_PROGRESS")
                .build();

        smsDao.save(temp);
        SendSmsKafkaRequest obj=new SendSmsKafkaRequest();
        obj.setId(temp.getId());
        //String kafkaMessage="Id: "+temp.getId()+", phone: "+temp.getPhone_number()+", message: "+temp.getMessage();
        //kafkaTemplate.send(topic,obj);
        kafkaProducer.publishToKafka(obj);
        System.out.println("Published successfully!");
        return temp;

    }

    @Override
    public Optional<Sms> getSmsById(String id) {

        Optional<Sms> temp=smsDao.findById(Integer.valueOf(id));
        if (!temp.isPresent()){
            throw new ApiException("Sms id not present");
            //return null;
        }
        return temp;
    }

    @Override
    public boolean block(Phone temp) {
        boolean result=phoneDao.save(temp);
        return result;
    }

    @Override
    public boolean unblock(Phone temp) {
        if (!temp.getPhone_number().matches("^[+]?[0-9]{0,2}?[0-9]{10}$"))
        {
            //throw new GlobalExceptionHandler();
        }

        boolean result=phoneDao.delete(temp);
        return result;
    }

    @Override
    public List<Phone> findAll() {
        return phoneDao.findAll();
    }
}
