package com.merchant.api.model.payload.request;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CustomerInfoReq {
	
	private Date dateOfBirth;
	private Integer dependentNumber;
	private Boolean ektpFlag;
	private String emailAddress;
	private String firstName;
	private String lastName;
	private String genderType;
	private String ktpNumber;
	private String lastEducation;
	private String maritalStatus;
	private String motherName;
	private String phoneNumber;
	private String birthplaceCode;
	private List<CustomerAddressReq> customerAddress;
}
