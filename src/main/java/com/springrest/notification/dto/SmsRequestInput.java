package com.springrest.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequestInput {

    @Pattern(regexp="^[+]?[0-9]{0,2}?[0-9]{10}$",message="Invalid Phone Number!")
    private String phone_number;
    private String message;


}
