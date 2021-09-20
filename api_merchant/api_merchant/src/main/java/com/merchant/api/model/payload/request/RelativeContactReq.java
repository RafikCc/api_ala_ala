package com.merchant.api.model.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RelativeContactReq {

    private String relativeName;
    private String relativePhone;
    private String relativeRelation;
}
