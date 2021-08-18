/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logistic.api.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author PROSIA
 */
@Entity
@Table(name = "trx_application")
@EqualsAndHashCode(callSuper = false, of = {"appId"})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TrxApplication extends AbstractAuditingEntity implements Serializable {
    
    public enum Status {
        REQUEST_PICKUP,
        PICKUP_BY_CURRIER,
        ARRIVED_DROP_POINT,
        DELIVERY_TO_CUSTOMER,
        DELIVERED
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id", unique = true, nullable = false)
    private Long appId;
    
    @Column(name = "address_from")
    @Lob
    private String addressFrom;
    
    @Column(name = "address_to")
    @Lob
    private String addressTo;
    
    @Column(name = "height", length = 20)
    private String height;
    
    @Column(name = "weight", length = 20)
    private String weight;
    
    @Column(name = "width", length = 20)
    private String width;
    
    @Column(name = "no_resi", length = 50)
    private String noResi;
    
    @Column(name = "status", length = 255)
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Column(name = "transaction_id", length = 50)
    private String transactionId;
    
    @Column(name = "order_no", length = 50)
    private String orderNo;
    
    @Column(name = "total_price")
    private Double totalPrice;
    
    @Column(name = "delivery_price")
    private Double deliveryPrice;
}
