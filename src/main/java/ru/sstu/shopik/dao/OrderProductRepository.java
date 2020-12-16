package ru.sstu.shopik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.sstu.shopik.domain.entities.*;

import java.util.Optional;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    Optional<OrderProduct> findByOrderAndProduct(Order order, Product product);

    @Query("Select op from OrderProduct op left join op.order c where c.status = :status and c.buyer = :user and op.product = :product")
    Optional<OrderProduct> findByStatusAndBuyerAndProduct(OrderStatus status, User user, Product product);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "DELETE order_product FROM order_product INNER JOIN orders ON orders.order_id = order_product.order_id WHERE orders.status = ? AND order_product.product_id = ?")
    void deleteByProductAndStatus(int status, long productId);

    long countByProduct(Product product);
}
