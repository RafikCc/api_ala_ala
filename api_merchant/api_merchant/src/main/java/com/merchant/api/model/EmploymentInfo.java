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
@Table(name = "trx_employment_info")
@EqualsAndHashCode(callSuper = false, of = {"employmentInfoId"})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class EmploymentInfo extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum EmploymentType {
        PRIVATE_SECTOR_EMPLOYEE,
        STATE_EMPLOYEE,
        SELF_EMPLOYED,
        CIVIL_SERVICE,
        STUDENT,
        PERSON_IN_HOUSEHOLD,
        RETIRED,
        UNEMPLOYED
    }

    public enum IndustryType {
        MANUFACTURING,
        GOVERNMENT,
        AGRICULTURE,
        TRADE,
        REAL_ESTATE,
        HEALTH,
        EDUCATION,
        ARMY_POLICE,
        LAWYER,
        COMMERCIAL,
        FINANCIAL,
        POLITICIAN,
        SERVICES,
        MINING,
        OIL_AND_GAS
    }

    public enum Profession {
        REGULAR_STAFF,
        ADMINISTRATOR,
        NON_STAFF,
        ASS_MANAGER,
        MIDDLE_MANAGEMENT,
        TOP_MANAGEMENT,
        SUPERVISOR,
        OWNER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long employmentInfoId;

    @Column(name = "employment_type", length = 255)
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Column(name = "industry_type", length = 255)
    @Enumerated(EnumType.STRING)
    private IndustryType industryType;

    @Column(name = "length_of_employment")
    private Integer lengthOfEmployment;

    @Column(name = "monthly_income")
    private Double monthlyIncome;

    @Column(name = "other_installment")
    private Double otherInstallment;

    @Column(name = "profession", length = 255)
    @Enumerated(EnumType.STRING)
    private Profession profession;

    @Column(name = "company_name", length = 255)
    private String companyName;

    @JoinColumn(name = "app_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Application application;
}
