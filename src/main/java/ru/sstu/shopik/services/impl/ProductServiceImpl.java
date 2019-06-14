package ru.sstu.shopik.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sstu.shopik.dao.CategoryRepository;
import ru.sstu.shopik.dao.ProductRepository;
import ru.sstu.shopik.dao.UserRepository;
import ru.sstu.shopik.domain.UserDetailsImpl;
import ru.sstu.shopik.domain.entities.Category;
import ru.sstu.shopik.domain.entities.Product;
import ru.sstu.shopik.exceptions.ProductDoesNotExist;
import ru.sstu.shopik.forms.ProductAddForm;
import ru.sstu.shopik.forms.ProductChangeForm;
import ru.sstu.shopik.services.ImageProductService;
import ru.sstu.shopik.services.MailService;
import ru.sstu.shopik.services.ProductService;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired

    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    MailService mailService;

    @Override
    public void delete(Product product) {
        Optional<Product> productFromDB = productRepository.findByProductName(product.getProductName());
        if (productFromDB.isPresent()) {
            productRepository.delete(product);
        }
    }

    @Override
    public Page<Product> getAllByNameForSearchInGeneralCategory(String productName, Pageable pageable) {
        return productRepository.findAllByProductNameContainingIgnoreCaseAndDeleted(productName, pageable, false);
    }

    @Override
    public void editProduct(Product product) {
        Optional<Product> productFromDB = productRepository.findByProductName(product.getProductName());
        if (productFromDB.isPresent()) {
            productRepository.save(product);
        }
    }

    @Autowired
    ImageProductService imageProductService;

    @Override
    public Optional<Product> getInfoAboutProductForBigPageById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAllByDeleted(pageable, false);
    }

    @Override
    public Page<Product> getAllByMotherCategoryAndProductName(String categoryName, String productName, Pageable pageable) {
        Optional<Category> motherCategory = categoryRepository.findByEnCategory(categoryName);
        if (motherCategory.isPresent()) {
            if (productName.equals("")) {
                return productRepository.productWithMotherCategory(motherCategory.get().getCategoryId(), pageable);
            } else {
                return productRepository.productWithMotherCategoryAndProductName(motherCategory.get().getCategoryId(),
                        productName, pageable);
            }
        } else {
            return Page.empty();
        }
    }

    @Override
    public Page<Product> getAllByCategoryAndProductName(String categoryName, String productName, String motherCategoryName, Pageable pageable) {
        Optional<Category> category = categoryRepository.findByEnCategory(categoryName);
        Optional<Category> motherCategory = categoryRepository.findByEnCategory(motherCategoryName);
        if (category.isPresent() && motherCategory.isPresent()) {
            if (productName.equals("")) {
                return productRepository.findAllByCategoryAndDeleted(category.get(), false, pageable);
            } else {
                return productRepository.findAllByCategoryAndProductNameContainingIgnoreCaseAndDeleted(category.get(), productName,
                        false, pageable);
            }
        } else {
            return Page.empty();
        }
    }

    @Override
    public void createProductFromAddProductForm(ProductAddForm productAddForm) throws  IOException{
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

    @Override
    public Page<Product> getPageProduct(int page) {
        return this.productRepository.findByDeleted(false, PageRequest.of(page, 5));
    }

    @Override
    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public void deleteProduct(Long id) {
        if (this.productRepository.countById(id) != 0) {
            this.productRepository.deleteById(id);
        }
    }

    @Override
    public void changeProduct(ProductChangeForm productChangeForm, long id) throws ProductDoesNotExist {
        Optional<Product> optionalProduct = this.getProductById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setProductName(productChangeForm.getProductName());
            product.setDescription(productChangeForm.getDescription());
            product.setCost(productChangeForm.getCost());
            product.setCategory(this.categoryRepository.findByEnCategoryOrRuCategory(productChangeForm.getMotherCategory(), productChangeForm.getMotherCategory()).orElse(null));
            this.productRepository.save(product);
            this.mailService.sendProductChange(product);
        } else {
            throw new ProductDoesNotExist();
        }
    }
}
