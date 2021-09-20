package com.merchant.api.model.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OrderItemsReq {

    private String itemId;
    private String itemImageUrl;
    private String itemName;
    private Integer itemPrice;
    private Integer itemQuantity;
    private String itemType;
    private String itemUrl;
    private String sellerBadge;
    private String sellerName;
    private String sellerId;
}
