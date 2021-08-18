/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logistic.api.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class ItemsReq {
    
    private String itemId;
    private String itemImageUrl;
    private String itemName;
    private Double itemPrice;
    private Integer itemQuantity;
    private String itemType;
    private String itemUrl;
    private String sellerBadge;
    private String sellerName;
    private String sellerId;
}
