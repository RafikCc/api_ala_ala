package com.merchant.api.model.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CustomerAddressReq {
	
	private String addressType;
    private String streetAddress;
    private String cityCode;
    private String districtCode;
    private String villageCode;
    private String rtNumber;
    private String rwNumber;
    private String zipcode;
}
