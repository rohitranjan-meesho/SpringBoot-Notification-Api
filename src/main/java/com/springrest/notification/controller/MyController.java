package com.springrest.notification.controller;

import com.springrest.notification.dto.SearchRequestDTO;
import com.springrest.notification.dto.SmsRequestInput;
import com.springrest.notification.entity.Phone;
import com.springrest.notification.entity.Sms;
import com.springrest.notification.entity.SmsES;

import com.springrest.notification.services.SearchService;
import com.springrest.notification.services.SmsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import java.util.*;


@RestController
public class MyController {
    private static final String number_template = "phone_number : %s";
    private static final String message_template = "message : %s";

    @Autowired
    private SmsService smsService;

    @Autowired
    private SearchService searchService;



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
    public ResponseEntity<?> addRequest(@Valid @RequestBody SmsRequestInput input) throws MethodArgumentNotValidException {
        //add request in db


        try{
            Sms ret=smsService.addSms(input);
            return ResponseEntity.of(Optional.of(ret));
        }
        catch(Exception e)
        {

            return new ResponseEntity<>("{'description': '" + e.getMessage() + "'}", HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/blacklist")
    public ResponseEntity<String> blacklist(@RequestBody Phone phone)
    {
        boolean result = this.smsService.block(phone);
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
        List<Phone> blocked_numbers=this.smsService.findAll();
        return ResponseEntity.ok(blocked_numbers);
    }

    @DeleteMapping(path="/unblock/{phone}")
    public ResponseEntity<String> unblock(@PathVariable(value="phone") Phone phone)
    {
        boolean result=this.smsService.unblock(phone);
        if(result)
        {
            return ResponseEntity.ok("Phone number unblocked successfully!");
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping(path="/search")
    public List<SmsES> search(@RequestBody SearchRequestDTO searchRequest)
    {
        return this.searchService.search(searchRequest);
    }

    @GetMapping(path="/search/all")
    public List<SmsES> searchAll()
    {
        return this.searchService.findAll();
    }

}
