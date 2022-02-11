package com.springrest.notification.elasticsearch;

import com.springrest.notification.entity.Sms;
import com.springrest.notification.entity.SmsES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


public interface ESDao extends ElasticsearchRepository<SmsES, String> {
}
