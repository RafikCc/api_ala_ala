/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logistic.api.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "trx_items")
@EqualsAndHashCode(callSuper = false, of = {"id"})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item extends AbstractAuditingEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    
    @Column(name = "item_id", length = 100)
    private String itemId;

    @Column(name = "item_img_url")
    @Lob
    private String itemImageUrl;

    @Column(name = "item_name", length = 100)
    private String itemName;

    @Column(name = "item_price")
    private Double itemPrice;

    @Column(name = "item_quantity")
    private Integer itemQuantity;

    @Column(name = "item_type", length = 100)
    private String itemType;

    @Column(name = "item_url")
    @Lob
    private String itemUrl;

    @Column(name = "seller_badge", length = 50)
    private String sellerBadge;

    @Column(name = "seller_name", length = 100)
    private String sellerName;

    @Column(name = "seller_id", length = 100)
    private String sellerId;

    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TrxApplication application;
}
