/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logistic.api.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class PickupReq {
    private String transactionId;
    private String orderId;
    private String contractCode;
    private String totalPrice;
    private String statusRequest;
    private String from;
    private String to;
    private String height;
    private String weight;
    private String width;
    private List<ItemsReq> items;
}
