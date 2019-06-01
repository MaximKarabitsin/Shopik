package ru.sstu.shopik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sstu.shopik.domain.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductName(String productName);
}
