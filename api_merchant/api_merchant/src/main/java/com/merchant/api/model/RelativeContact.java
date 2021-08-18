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
@Table(name = "trx_relative_contact")
@EqualsAndHashCode(callSuper = false, of = {"relativeContactId"})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class RelativeContact extends AbstractAuditingEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public enum RelativeRelation {
        SPOUSE, 
        FATHER, 
        MOTHER, 
        BROTHER, 
        SISTER, 
        SON, 
        DAUGHTER, 
        OTHER_RELATIVE
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long relativeContactId;
	
	@Column(name = "relative_name", length = 100)
	private String relativeName;
	
	@Column(name = "relative_phone", length = 20)
	private String relativePhone;
	
	@Column(name = "relative_relation", length = 50)
	@Enumerated(EnumType.STRING)
	private RelativeRelation relativeRelation;
	
	@JoinColumn(name = "app_id", referencedColumnName = "id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Application application;
}
