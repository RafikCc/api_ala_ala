package com.merchant.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "trx_bank_info")
@EqualsAndHashCode(callSuper = false, of = {"bankInfoId"})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class BankInfo extends AbstractAuditingEntity  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long bankInfoId;
	
	@Column(name = "bank_code", length = 100)
	private String bankCode;
	
	@Column(name = "bank_name", length = 100)
	private String bankName;
	
	@Column(name = "account_number")
	private Long accountNumber;
	
	@Column(name = "account_name", length = 255)
	private String accountName;
	
	@JoinColumn(name = "app_id", referencedColumnName = "id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Application application;
}
