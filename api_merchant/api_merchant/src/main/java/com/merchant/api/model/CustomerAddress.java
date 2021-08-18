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
@Table(name = "trx_customer_address")
@EqualsAndHashCode(callSuper = false, of = {"custAdressId"})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class CustomerAddress extends AbstractAuditingEntity  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public enum AddressType {
        PERMANENT, CONTRACT
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long custAdressId;
	
	@JoinColumn(name = "cust_info_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CustomerInfo custInfoId;
    
    @Column(name = "address_type", length = 50)
    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    
    @Column(name = "street_address")
    @Lob
    private String streetAddress;
    
    @Column(name = "city_code", length = 20)
    private String cityCode;
    
    @Column(name = "district_code", length = 20)
    private String districtCode;
    
    @Column(name = "village_code", length = 20)
    private String villageCode;
    
    @Column(name = "rt_number", length = 3)
    private String rtNumber;
    
    @Column(name = "rw_number", length = 3)
    private String rwNumber;
    
    @Column(name = "zipcode", length = 5)
    private String zipcode;
}
