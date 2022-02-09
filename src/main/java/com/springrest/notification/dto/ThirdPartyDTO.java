package com.springrest.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThirdPartyDTO {
    private String code;
    private String transid;
    private String description;
    private String correlationid;
}
