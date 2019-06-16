package ru.sstu.shopik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sstu.shopik.domain.entities.*;

import java.util.Optional;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    Optional<OrderProduct> findByOrderAndProduct(Order order, Product product);

    @Query("Select op from OrderProduct op left join op.order c where c.status = :status and c.buyer = :user and op.product = :product")
    Optional<OrderProduct> findByStatusAndBuyerAndProduct(OrderStatus status, User user, Product product);
}
