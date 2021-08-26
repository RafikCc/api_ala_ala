/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merchant.api.model.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.merchant.api.model.payload.request.OrderItemsReq;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author PROSIA
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryResponse implements Serializable {
    private String name;
    private String accountType;
    private List<OrderItemsReq> history;
}
