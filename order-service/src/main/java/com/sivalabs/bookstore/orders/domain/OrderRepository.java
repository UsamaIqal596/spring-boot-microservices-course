package com.sivalabs.bookstore.orders.domain;

import com.sivalabs.bookstore.orders.domain.models.OrderStatus;
import com.sivalabs.bookstore.orders.domain.models.OrderSummary;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByStatus(OrderStatus status);

    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    default void updateOrderStatus(String orderNumber, OrderStatus status) {
        OrderEntity order = this.findByOrderNumber(orderNumber).orElseThrow();
        order.setStatus(status);
        this.save(order);
    }

    // Below I have used new com.sivalabs.bookstore.orders.domain.models.OrderSummary this to get only two fields from
    // DB
    @Query(
            """
        select new com.sivalabs.bookstore.orders.domain.models.OrderSummary(o.orderNumber, o.status)
        from OrderEntity o
        where o.userName = :userName
        """)
    List<OrderSummary> findByUserName(String userName);

    // I have used below to resolve the n+1 query problem as if i don't use below there will be two queries called
    // Hibernate: select
    // oe1_0.id,oe1_0.comments,oe1_0.created_at,oe1_0.customer_name,oe1_0.customer_email,oe1_0.customer_phone,oe1_0.delivery_address_line1,oe1_0.delivery_address_line2,oe1_0.delivery_address_city,oe1_0.delivery_address_state,oe1_0.delivery_address_zip_code,oe1_0.delivery_address_country,oe1_0.order_number,oe1_0.status,oe1_0.updated_at,oe1_0.username
    // from **orders** oe1_0 where oe1_0.username=? and oe1_0.order_number=?

    // Hibernate: select i1_0.order_id,i1_0.id,i1_0.code,i1_0.name,i1_0.price,i1_0.quantity
    // from **order_items** i1_0 where i1_0.order_id=?
    // I have used below to there will be only one query will be executed
    @Query(
            """
        select distinct o
        from OrderEntity o left join fetch o.items
        where o.userName = :userName and o.orderNumber = :orderNumber
        """)
    Optional<OrderEntity> findByUserNameAndOrderNumber(String userName, String orderNumber);
}
