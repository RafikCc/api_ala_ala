package com.merchant.api.model.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class EmploymentInfoReq {

    private String employmentType;
    private String industryType;
    private Integer lengthOfEmployment;
    private Integer monthlyIncome;
    private Integer otherInstallment;
    private String profession;
    private String companyName;
}
