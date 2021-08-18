/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logistic.api.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logistic.api.payload.request.ItemsReq;
import java.util.List;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class InquiryResponse {
    private String transactionId;
    private String orderId;
    private String contractCode;
    private String totalPrice;
    private String noResi;
    private String statusDelivery;
    private List<ItemsReq> items;
}
