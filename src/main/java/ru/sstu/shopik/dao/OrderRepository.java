package ru.sstu.shopik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sstu.shopik.domain.entities.Category;
import ru.sstu.shopik.domain.entities.Order;
import ru.sstu.shopik.domain.entities.OrderStatus;
import ru.sstu.shopik.domain.entities.User;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByBuyerAndStatus(User buyer, OrderStatus status);

}
