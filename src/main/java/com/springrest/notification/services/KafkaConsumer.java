package com.springrest.notification.services;

import com.springrest.notification.dao.SmsDao;
import com.springrest.notification.dto.SendSmsKafkaRequest;
import com.springrest.notification.dto.ThirdPartyDTO;
import com.springrest.notification.entity.Sms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class KafkaConsumer {

    @Autowired
    SmsService smsService;
    @Autowired
    SearchService searchService;

    @Autowired
    SmsDao smsDao;

    @Autowired
    ThirdPartyService thirdPartyService;

    @KafkaListener(topics = "mytopic", groupId = "group_id",containerFactory = "smsKafkaListenerFactory")
    public void consume(SendSmsKafkaRequest message) throws IOException {
        System.out.println("Consumed message: " + message);
        String id=String.valueOf(message.getId());
        Sms temp=smsService.getSmsById(id).orElse(null);
        if (temp==null)
        {
            return;
        }



//   ********************    3rd Party Api call           *******************


        ThirdPartyDTO apiResponse = thirdPartyService.callThirdParty(temp);
        if (apiResponse.getCode()=="-1")
        {
            //failure
            temp.setStatus("FAILED");
            temp.setFailure_code(Integer.valueOf(apiResponse.getCode()));
            temp.setFailure_comments(apiResponse.getDescription());


        }
        else
        {
            temp.setStatus("SUCCESS");
            temp.setFailure_code(Integer.valueOf(apiResponse.getCode()));
            temp.setFailure_comments(apiResponse.getDescription());
        }

        //save request updates
        smsDao.save(temp);
        searchService.index(temp);


    }

//
//    @KafkaListener(topics = "mytopic_json", groupId = "group_json",
//            containerFactory = "smsKafkaListenerFactory")
//    public void consumeJson(Sms sms) {
//        System.out.println("Consumed JSON Message: " + sms);
//    }
}
