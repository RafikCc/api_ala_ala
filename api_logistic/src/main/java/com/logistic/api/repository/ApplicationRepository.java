/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logistic.api.repository;

import com.logistic.api.model.TrxApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PROSIA
 */
@Repository
public interface ApplicationRepository extends JpaRepository<TrxApplication, Long>,
        JpaSpecificationExecutor<TrxApplication> {
    public TrxApplication findByNoResi(String noResi);
}
