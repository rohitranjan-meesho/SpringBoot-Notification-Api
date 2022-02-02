package com.springrest.notification.dao;

import com.springrest.notification.entity.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsDao extends JpaRepository<Sms,Integer> {
}
