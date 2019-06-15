package ru.sstu.shopik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sstu.shopik.domain.entities.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByEnCategory(String name);

    Optional<Category> findByCategoryId(int id);

    Optional<Category> findByEnCategoryOrRuCategory(String enCategory, String ruCategory);

    @Query(nativeQuery = true, value = "SELECT * FROM category where mother_id = 2 ORDER BY RAND() LIMIT 1")
    Optional<Category> findRandomCategory();
}
