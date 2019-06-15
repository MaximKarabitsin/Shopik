package ru.sstu.shopik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sstu.shopik.domain.entities.*;

import java.util.Optional;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    Optional<OrderProduct> findByOrderAndProduct(Order order, Product product);

}
