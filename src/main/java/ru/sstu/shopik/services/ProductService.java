package ru.sstu.shopik.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sstu.shopik.domain.entities.Product;
import ru.sstu.shopik.forms.ProductAddForm;

import java.util.Optional;

public interface ProductService {
    void delete(Product product);

    Page<Product> getAllByNameForSearchInGeneralCategory(String productName, Pageable pageable);

    void editProduct(Product product);

    Optional<Product> getInfoAboutProductForBigPageById(Long id);

    Page<Product> getAll(Pageable pageable);

    Page<Product> getAllByMotherCategoryAndProductName(String categoryName, String productName, Pageable pageable);

    Page<Product> getAllByCategoryAndProductName(String categoryName, String productName, String motherCategoryName, Pageable pageable);

    void createProductFromAddProductForm(ProductAddForm productAddForm);
}


