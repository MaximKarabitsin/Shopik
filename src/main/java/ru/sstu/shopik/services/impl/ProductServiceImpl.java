package ru.sstu.shopik.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sstu.shopik.dao.CategoryRepository;
import ru.sstu.shopik.dao.ProductRepository;
import ru.sstu.shopik.domain.UserDetailsImpl;
import ru.sstu.shopik.domain.entities.Product;
import ru.sstu.shopik.forms.ProductAddForm;
import ru.sstu.shopik.services.ImageProductService;
import ru.sstu.shopik.services.ProductService;

import java.io.IOException;
import java.util.Date;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ImageProductService imageProductService;

    @Override
    public void createProductFromAddProductForm(ProductAddForm productAddForm) throws IOException {
        Product product = new Product();
        BeanUtils.copyProperties(productAddForm, product);
        long id = this.productRepository.getMaxId();
        product.setId(++id);
        product.setCategory(this.categoryRepository.findByEnCategoryOrRuCategory(productAddForm.getMotherCategory(), productAddForm.getMotherCategory()).orElse(null));
        product.setDate(new Date());
        product.setDiscount(0);
        product.setSeller(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());
        product.setDeleted(false);
        this.imageProductService.saveImage(productAddForm.getFiles(), id);
        this.productRepository.save(product);
    }
}
