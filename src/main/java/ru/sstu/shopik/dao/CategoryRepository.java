package ru.sstu.shopik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sstu.shopik.domain.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByCategory(String categoryName);
}
