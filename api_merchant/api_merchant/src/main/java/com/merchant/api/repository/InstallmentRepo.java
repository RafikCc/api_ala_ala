package com.merchant.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.merchant.api.model.Installment;

@Repository
public interface InstallmentRepo extends JpaRepository<Installment, Long>, JpaSpecificationExecutor<Installment> {

}
