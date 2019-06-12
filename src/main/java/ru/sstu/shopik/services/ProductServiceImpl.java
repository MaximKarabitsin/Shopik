package ru.sstu.shopik.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sstu.shopik.dao.CategoryRepository;
import ru.sstu.shopik.dao.ProductRepository;
import ru.sstu.shopik.domain.UserDetailsImpl;
import ru.sstu.shopik.domain.entities.Product;
import ru.sstu.shopik.forms.ProductAddForm;

import java.util.Date;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void createProductFromAddProductForm(ProductAddForm productAddForm) {
        Product product = new Product();
        BeanUtils.copyProperties(productAddForm, product);
        product.setCategory(this.categoryRepository.findByEnCategoryOrRuCategory(productAddForm.getMotherCategory(), productAddForm.getMotherCategory()).orElse(null));
        product.setDate(new Date());
        product.setDiscount(0);
        product.setSeller(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());
        product.setDeleted(false);
        this.productRepository.save(product);
    }
}
