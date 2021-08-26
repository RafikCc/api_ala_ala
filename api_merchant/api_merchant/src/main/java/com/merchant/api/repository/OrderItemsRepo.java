package com.merchant.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.merchant.api.model.OrderItems;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface OrderItemsRepo extends JpaRepository<OrderItems, Long>, JpaSpecificationExecutor<OrderItems> {

    @Query("select o from OrderItems o "
            + "left join fetch o.order "
            + "where o.itemId = ?2 "
            + "and o.order.application.appId = ?1 ")
    public OrderItems findByAppIdAndOrderItem(Long appId, String itemsId);

    @Query(value = "SELECT toi.* from trx_order_items toi "
            + "left join trx_order tor on toi.order_id = tor.id "
            + "left join trx_application ta on tor.app_id = ta.id "
            + "left join trx_customer_info tci on ta.id = tci.app_id "
            + "where tci.ktp_number = :noKtp "
            + "and tci.phone_number = :noHp "
            + "and ta.status = 'SUCCESSFUL'", 
            nativeQuery = true)
    public List<OrderItems> getHistoryByNoKtpAndNoHp(
            @Param("noKtp") String noKtp,
            @Param("noHp") String noHp);
}
