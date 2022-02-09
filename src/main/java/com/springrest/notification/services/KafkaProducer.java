package com.springrest.notification.services;

import com.springrest.notification.dto.SendSmsKafkaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    public static final String topic = "mytopic";
    @Autowired
    public KafkaTemplate<String, SendSmsKafkaRequest> kafkaTemplate;
    public void publishToKafka(SendSmsKafkaRequest id)
    {
        this.kafkaTemplate.send(topic,id);
    }

}
