package com.merchant.api.model.payload.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ApplicationReq {

    private AdditionalInfoReq additionalInfo;
    private BankInfoReq bankInfo;
    private CustomerInfoReq customerInfo;
    private List<DocumentPhotoReq> documentPhoto;
    private EmploymentInfoReq employmentInfo;
    private InstallmentReq installment;
    private OrderReq order;
    private List<RelativeContactReq> relativeContact;
}
