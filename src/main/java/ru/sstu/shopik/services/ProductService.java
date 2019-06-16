package ru.sstu.shopik.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import ru.sstu.shopik.domain.entities.Product;
import ru.sstu.shopik.domain.entities.WishList;
import ru.sstu.shopik.exceptions.ProductDoesNotExist;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.forms.ProductAddForm;
import ru.sstu.shopik.forms.ProductChangeForm;
import ru.sstu.shopik.forms.ProductChangeFormFromProfile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductService {
    Page<Product> getPageProduct(Pageable pageable);

    Optional<Product> getProductById(long id);

    void deleteProduct(Long id);


    void changeProduct(ProductChangeForm productChangeForm, long id) throws ProductDoesNotExist, IOException;


    void delete(Product product);

    Page<Product> getAllByNameForSearchInGeneralCategory(String productName, Pageable pageable);

    void editProduct(Product product);

    Optional<Product> getInfoAboutProductForBigPageById(Long id);

    Page<Product> getAll(Pageable pageable);

    Page<Product> getAllByMotherCategoryAndProductName(String categoryName, String productName, Pageable pageable);

    Page<Product> getAllByCategoryAndProductName(String categoryName, String productName, String motherCategoryName, Pageable pageable);

    Page<Product> getTenProductsForNovelties();

    List<Product> getTenWithSale();

    Set<Product> getTenWithRandomCategory();

    void createProductFromAddProductForm(ProductAddForm productAddForm) throws IOException;

    Page<Product> getAllProductsForTheSeller(Pageable pageable, Authentication authentication) throws UserDoesNotExist;

    void changeProductFromSeller(ProductChangeFormFromProfile productChangeFormFromProfile, long id) throws ProductDoesNotExist;

    Page<WishList> getWishLists(Pageable pageable, Authentication authentication) throws UserDoesNotExist;

    void addProductToWishList(Authentication authentication, long id);
}
