/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merchant.api.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.javers.core.metamodel.annotation.DiffIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author PROSIA
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
public abstract class AbstractAuditingEntity {
    @CreatedBy
    @Column(name = "created_by", updatable = false, length = 50)
    @DiffIgnore
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DiffIgnore
    private Date createdDate;

    @LastModifiedBy
    @Column(name = "last_modified_by", insertable = false, length = 50)
    @DiffIgnore
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DiffIgnore
    private Date lastModifiedDate;
}
