package com.merchant.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.merchant.api.model.OrderItems;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface OrderItemsRepo extends JpaRepository<OrderItems, Long>, JpaSpecificationExecutor<OrderItems> {
    
    @Query("select o from OrderItems o "
            + "left join fetch o.order "
            + "where o.itemId = ?2 "
            + "and o.order.application.appId = ?1 ")
    public OrderItems findByAppIdAndOrderItem(Long appId, String itemsId);
}
