package com.merchant.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.merchant.api.model.CustomerInfo;

@Repository
public interface CustomerInfoRepo extends JpaRepository<CustomerInfo, Long>, JpaSpecificationExecutor<CustomerInfo> {

}
