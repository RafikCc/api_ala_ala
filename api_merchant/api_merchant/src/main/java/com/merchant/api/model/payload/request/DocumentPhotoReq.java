package com.merchant.api.model.payload.request;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Setter
@Getter
@ToString
public class DocumentPhotoReq {

    private String documentType;
    private String documentUrl;
}
