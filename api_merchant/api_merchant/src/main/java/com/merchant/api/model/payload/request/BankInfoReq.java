package com.merchant.api.model.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BankInfoReq {
	
	private String bankCode;
	private String bankName;
	private Long accountNumber;
	private String accountName;
}
