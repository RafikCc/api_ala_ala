package com.merchant.api.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "trx_customer_info")
@EqualsAndHashCode(callSuper = false, of = {"custInfoId"})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class CustomerInfo extends AbstractAuditingEntity  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public enum Gender {
        M, F
    }
	
	public enum LastEducation {
        WITHOUT_EDUCATION, 
        ELEMENTARY_SCHOOL, 
        SMP, SMA, ACADEMY, 
        BACHELORS, MASTERS, 
        PHD
    }
	
	public enum MaritalStatus {
        SINGLE, MARRIED, DIVORCED, WIDOWED
    }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long custInfoId;
	
	@JoinColumn(name = "app_id", referencedColumnName = "id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Application application;
	
	@Column(name = "dob")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column(name = "dependent_number")
	private Integer dependentNumber;
	
	@Column(name = "ektp_flag")
	private Boolean ektpFlag;
	
	@Column(name = "email_address", length = 100)
	private String emailAddress;
	
	@Column(name = "first_name", length = 100)
	private String firstName;
	
	@Column(name = "last_name", length = 100)
	private String lastName;
	
	@Column(name = "gender_type", length = 10)
	@Enumerated(EnumType.STRING)
	private Gender genderType;
	
	@Column(name = "ktp_number", length = 20)
	private String ktpNumber;
	
	@Column(name = "last_education", length = 50)
	@Enumerated(EnumType.STRING)
	private LastEducation lastEducation;
	
	@Column(name = "marital_status", length = 50)
	@Enumerated(EnumType.STRING)
	private MaritalStatus maritalStatus;
	
	@Column(name = "mother_name", length = 100)
	private String motherName;
	
	@Column(name = "phone_number", length = 20)
	private String phoneNumber;
	
	@Column(name = "birthplace_code", length = 5)
	private String birthplaceCode;
}
