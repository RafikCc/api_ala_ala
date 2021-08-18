package com.merchant.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.merchant.api.model.Application;

@Repository
public interface ApplicationRepo extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {
    public Application findByOrderId(String orderId);
}
