package ru.sstu.shopik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sstu.shopik.domain.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductName(String productName);

    @Query("SELECT coalesce(max(pr.id), 0) FROM Product pr")
    Long getMaxId();
}
