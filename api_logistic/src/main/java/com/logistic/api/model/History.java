/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logistic.api.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author PROSIA
 */
@Entity
@Table(name = "trx_history_delivery")
@EqualsAndHashCode(callSuper = false, of = {"historyId"})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class History extends AbstractAuditingEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long historyId;
    
    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TrxApplication application;
    
    @Column(name = "history")
    @Lob
    private String history;
    
    @Column(name = "date_history")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateHistory;
}
