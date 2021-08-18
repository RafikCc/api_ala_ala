/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merchant.api.model.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author PROSIA
 */
@Setter
@Getter
@ToString
public class UpdateReq {
    //private String transactionId;
    private String orderId;
    //private String contractCode;
    private String status;
}
