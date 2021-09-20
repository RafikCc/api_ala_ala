package com.merchant.api.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "trx_additional_info")
@EqualsAndHashCode(callSuper = false, of = {"additionalInfoId"})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class AdditionalInfo extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long additionalInfoId;

    @Column(name = "customer_registration_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date customerRegistrationTime;

    @Column(name = "number_of_transaction")
    private Integer numberOfTransaction;

    @Column(name = "offer_code", length = 100)
    private String offerCode;

    @Column(name = "otp_verification_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otpVerificationTime;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "volume_of_transaction")
    private Double volumeOfTransaction;

    @JoinColumn(name = "app_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Application application;

}
