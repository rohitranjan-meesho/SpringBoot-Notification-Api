package com.springrest.notification.thirdparty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MSISDNS {

    private List<String> msisdn;
    private Integer correlationid;
}
