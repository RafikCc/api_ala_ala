package com.merchant.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.merchant.api.model.CustomerAddress;

@Repository
public interface CustomerAddressRepo extends JpaRepository<CustomerAddress, Long>, JpaSpecificationExecutor<CustomerAddress> {

}
