package com.springrest.notification.thirdparty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThirdPartyRequestModel {

    public String deliverychannel;
    public Channels channels;
    public List<MSISDNS> destination;
}
