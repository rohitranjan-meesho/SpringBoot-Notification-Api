package com.springrest.notification.services;

import com.springrest.notification.dao.SmsDao;
import com.springrest.notification.dto.ThirdPartyDTO;
import com.springrest.notification.dto.ThirdPartyResponseList;
import com.springrest.notification.entity.Sms;
import com.springrest.notification.thirdparty.Channels;
import com.springrest.notification.thirdparty.MSISDNS;
import com.springrest.notification.thirdparty.ThirdPartyRequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@Service
public class ThirdPartyService {

    private final String apiUrl = "https://api.imiconnect.in/resources/v1/messaging";
    private final Logger logger = LoggerFactory.getLogger(ThirdPartyService.class);
    private final String contentType = "application/json";
    //private final String key="xxxxxxxxxxxxxxxxxxx"
    


    @Autowired
    private  SmsDao smsDao;

    public ThirdPartyDTO callThirdParty (Sms sms) throws IOException
    {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type",contentType);
        headers.set("key",key);
        ThirdPartyRequestModel thirdPartyRequestModel=ThirdPartyRequestModel.builder()
                .deliverychannel("sms")
                .channels(new Channels(sms.getMessage()))
                .destination(Arrays.asList(new MSISDNS(Arrays.asList(sms.getPhone_number()),sms.getId())))
                .build();


        HttpEntity<ThirdPartyRequestModel> httpRequest = new HttpEntity<>(thirdPartyRequestModel, headers);

        //ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, httpRequest, String.class);
        ResponseEntity<ThirdPartyResponseList> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, httpRequest, ThirdPartyResponseList.class);

        try {


            ThirdPartyResponseList response = responseEntity.getBody();
            return response.getResponse().get(0);
        }
        catch(Exception e)
        {
            ThirdPartyDTO failure =ThirdPartyDTO.builder()
                    .code("-1")
                    .correlationid("-1")
                    .description("-1")
                    .transid("-1")
                    .build();
            return failure;
        }

    }
}
