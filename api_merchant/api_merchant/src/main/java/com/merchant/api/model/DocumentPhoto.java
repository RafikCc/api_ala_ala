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
@Table(name = "trx_doc_photo")
@EqualsAndHashCode(callSuper = false, of = {"docPhotoId"})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class DocumentPhoto extends AbstractAuditingEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public enum DocumentType {
        KTP, SELFIE
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long docPhotoId;
	
	@Column(name = "doc_type", length = 100)
	@Enumerated(EnumType.STRING)
	private DocumentType documentType;
	
	@Column(name = "doc_url", length = 255)
	private String documentUrl;
	
	@JoinColumn(name = "app_id", referencedColumnName = "id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Application application;
	
}
