package com.merchant.api.model;

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

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "trx_order")
@EqualsAndHashCode(callSuper = false, of = {"oId"})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class Orders extends AbstractAuditingEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long oId;
	
	@Column(name = "invoice_no", length = 255)
	private String invoiceNumber;
	
	@Column(name = "shipping_address")
	@Lob
	private String shippingAddress;
	
	@Column(name = "total_price")
	private Double totalPrice;
	
	@JoinColumn(name = "app_id", referencedColumnName = "id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Application application;
}
