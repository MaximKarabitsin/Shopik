package ru.sstu.shopik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sstu.shopik.domain.entities.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByEnCategory(String name);

    Optional<Category> findByEnCategoryOrRuCategory(String enCategory, String ruCategory);
}
