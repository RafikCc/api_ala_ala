package com.merchant.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.merchant.api.model.RelativeContact;

@Repository
public interface RelativeContactRepo extends JpaRepository<RelativeContact, Long>, JpaSpecificationExecutor<RelativeContact> {

}
