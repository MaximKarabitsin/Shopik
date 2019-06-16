package ru.sstu.shopik.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sstu.shopik.domain.entities.Category;
import ru.sstu.shopik.domain.entities.Product;
import ru.sstu.shopik.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByDeleted(boolean param, Pageable pageable);

    int countById(Long id);

    @Query("SELECT coalesce(max(pr.id), 0) FROM Product pr")
    Long getMaxId();

    Optional<Product> findByProductName(String productName);

    Optional<Product> findById(Long id);

    Page<Product> findAllByProductNameContainingIgnoreCaseAndDeleted(String productName, Pageable pageable, boolean deleted);

    Page<Product> findAllByDeleted(Pageable pageable, boolean deleted);

    @Query("Select p from Product p left join p.category c where c.motherCategory.id = :id and p.deleted = false ")
    Page<Product> productWithMotherCategory(@Param("id") int id, Pageable pageable);

    @Query("select p from Product p left join p.category c where c.motherCategory.id = :id and " +
            "lower(p.productName) like lower(concat('%', :name, '%')) and p.deleted = false ")
    Page<Product> productWithMotherCategoryAndProductName(@Param("id") int id, @Param("name") String name,
                                                          Pageable pageable);

    Page<Product> findAllByCategoryAndDeleted(Category category, boolean deleted, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM product where category_id =?1 and deleted = false ORDER BY RAND() LIMIT 10")
    List<Product> findTenProductsWithRandomCategory(int categoryId);

    Page<Product> findAllByCategoryAndProductNameContainingIgnoreCaseAndDeleted(Category category, String productName,
                                                                                boolean deleted, Pageable pageable);


    @Query(nativeQuery = true, value = "SELECT * FROM product where discount <> 0 and deleted = false ORDER BY RAND() LIMIT 10")
    List<Product> findTenProductsWithSale();

    Page<Product> findAllBySeller(User seller, Pageable pageable);

}
