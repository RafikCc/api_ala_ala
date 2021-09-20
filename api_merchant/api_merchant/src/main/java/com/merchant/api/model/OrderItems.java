package com.merchant.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "trx_order_items")
@EqualsAndHashCode(callSuper = false, of = {"orderItemsId"})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class OrderItems extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum StatusItem {
        RUSAK,
        SALAH_BARANG,
        KONDISI_TIDAK_SESUAI
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long orderItemsId;

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

    @Column(name = "status_item", length = 50)
    @Enumerated(EnumType.STRING)
    private StatusItem status;

    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Orders order;
}
