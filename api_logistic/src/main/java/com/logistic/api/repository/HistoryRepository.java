/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logistic.api.repository;

import com.logistic.api.model.History;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PROSIA
 */
@Repository
public interface HistoryRepository extends JpaRepository<History, Long>, 
        JpaSpecificationExecutor<History>{
    @Query("select h from History h "
            + "where h.application.appId = ?1")
    public List<History> findAllHistoryByAppId(Long appId);
}
