package com.merchant.api.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "trx_application")
@EqualsAndHashCode(callSuper = false, of = {"appId"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@DynamicUpdate
public class Application extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Status {
        PENDING,
        PENDING_OUT_OF_OFFICE,
        CANCELLED,
        FAILED,
        IN_PROCESS,
        CS_REJECTED,
        CS_APPROVED,
        PRIVY_REGISTRATION,
        E_SIGNED,
        READY_TO_DISBURSE,
        DISBURSED,
        NEW_REQUEST,
        SUCCESSFUL,
        RETURN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long appId;

    @Column(name = "consent_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date consentTimeStamp;

    @Column(name = "expired_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredTime;

    @Column(name = "order_id", length = 50)
    private String orderId;

    @Column(name = "status", length = 255)
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Column(name = "down_payment_amount")
    private Double downPaymentAmount;
    
    @Column(name = "dp_status_payment", length = 5)
    private String dpStatusPayment;
    
    @Column(name = "dp_payment_expired_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dpPaymentExpiredDate;
}
