package com.merchant.api.model.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class InstallmentReq {

    private Integer amount;
    private Integer monthInstallment;
}
