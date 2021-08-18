package com.merchant.api.model.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class InstallmentReq {

	private Double amount;
	private Integer monthInstallment;
}
