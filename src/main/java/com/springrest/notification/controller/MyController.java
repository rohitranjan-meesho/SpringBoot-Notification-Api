package com.springrest.notification.controller;

import com.springrest.notification.entity.Phone;
import com.springrest.notification.entity.Sms;
import com.springrest.notification.exception.GlobalExceptionHandler;
import com.springrest.notification.services.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.function.*;

@RestController
public class MyController {
    private static final String number_template = "phone_number : %s";
    private static final String message_template = "message : %s";

    @Autowired
    private SmsService service;



    @GetMapping("/sms")
    public String test()
    {
        return "Welcome to SpringBoot!";
    }
//    @GetMapping("/sms/send")
//    public Sms send_sms(@Valid @RequestParam String number, @RequestParam String message) {
//        return new Sms(String.format(number_template,number), String.format(message_template, message));
//    }

    @PostMapping("/sms/send")
    public ResponseEntity<?> addRequest(@Valid @RequestBody Map<String,String> map) throws MethodArgumentNotValidException {
        //add request in db
        System.out.println(map.get("phone_number"));
        Sms temp=Sms.builder()
                .phone_number(map.get("phone_number"))
                .message(map.get("message"))
                .status("IN_PROGRESS")
//                .failure_code(0)
//                .failure_comments("none")

                //.failure_comments(null)
                .build();
        try{
            this.service.addSms(temp);
            return ResponseEntity.of(Optional.of(temp));
        }
        catch(Exception e)
        {

//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST).build();


           //return new ResponseEntity<Sms>(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("{'description': '" + e.getMessage() + "'}", HttpStatus.BAD_REQUEST);
        }

        //return this.service.addSms(temp);

    }

    @PostMapping("/blacklist")
    public ResponseEntity<String> blacklist(@RequestBody Phone phone)
    {
        boolean result = this.service.block(phone);
        if (result)
        {
            return ResponseEntity.ok("Phone number blacklisted successfully!");
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
    @GetMapping("/blacklist")
    public ResponseEntity<List<Phone>> getAllBlocked()
    {
        List<Phone> blocked_numbers=this.service.findAll();
        return ResponseEntity.ok(blocked_numbers);
    }
}
