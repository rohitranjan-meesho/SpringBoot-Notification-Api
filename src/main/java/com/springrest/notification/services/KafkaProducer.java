package com.springrest.notification.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    public static final String topic = "mytopic";
    @Autowired
    public KafkaTemplate<String,String> kafkaTemplate;
    public void publishToKafka(Integer id)
    {
        this.kafkaTemplate.send(topic,String.valueOf(id));
    }

}
