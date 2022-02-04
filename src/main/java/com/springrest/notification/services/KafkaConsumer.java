package com.springrest.notification.services;

import com.springrest.notification.entity.Sms;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "mytopic", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }


    @KafkaListener(topics = "mytopic_json", groupId = "group_json",
            containerFactory = "userKafkaListenerFactory")
    public void consumeJson(Sms sms) {
        System.out.println("Consumed JSON Message: " + sms);
    }
}
