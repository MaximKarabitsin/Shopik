package ru.sstu.shopik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sstu.shopik.domain.entities.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    OrderStatus findByStatus(String status);

}
