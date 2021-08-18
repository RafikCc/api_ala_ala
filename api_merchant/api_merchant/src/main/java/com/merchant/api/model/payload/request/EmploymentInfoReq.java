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
	private Double monthlyIncome;
	private Double otherInstallment;
	private String profession;
}
