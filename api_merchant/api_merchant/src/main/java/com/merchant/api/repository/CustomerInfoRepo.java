package com.merchant.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.merchant.api.model.CustomerInfo;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface CustomerInfoRepo extends JpaRepository<CustomerInfo, Long>, JpaSpecificationExecutor<CustomerInfo> {
    
    @Query("select a from CustomerInfo a "
            + "where a.ktpNumber = ?1 "
            + "and a.phoneNumber = ?2 "
            + "and a.application.status = 'SUCCESSFUL'")
    public List<CustomerInfo> getByNoKtpAndNoHp(String noKtp, String noHp);
}
