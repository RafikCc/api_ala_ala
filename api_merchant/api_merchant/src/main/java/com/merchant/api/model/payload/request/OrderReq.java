package com.merchant.api.model.payload.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OrderReq {

    private String shippingAddress;
    private Integer totalPrice;
    private List<OrderItemsReq> items;
}
