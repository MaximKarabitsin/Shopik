package ru.sstu.shopik.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sstu.shopik.dao.CategoryRepository;
import ru.sstu.shopik.dao.ProductRepository;
import ru.sstu.shopik.dao.UserRepository;
import ru.sstu.shopik.dao.WishListRepository;
import ru.sstu.shopik.domain.UserDetailsImpl;
import ru.sstu.shopik.domain.entities.Category;
import ru.sstu.shopik.domain.entities.Product;
import ru.sstu.shopik.domain.entities.User;
import ru.sstu.shopik.domain.entities.WishList;
import ru.sstu.shopik.exceptions.ProductDoesNotExist;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.forms.ProductAddForm;
import ru.sstu.shopik.forms.ProductChangeForm;
import ru.sstu.shopik.forms.ProductChangeFormFromProfile;
import ru.sstu.shopik.services.*;

import java.io.IOException;
import java.util.*;


@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MailService mailService;

    @Autowired
    private ImageProductService imageProductService;

    @Autowired
    private UserService userService;

    @Autowired
    private WishListRepository wishListRepository;

    @Override
    public void delete(Product product) {
        Optional<Product> productFromDB = productRepository.findByProductNameAndDeleted(product.getProductName(), false);
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
        Optional<Product> productFromDB = productRepository.findByProductNameAndDeleted(product.getProductName(), false);
        if (productFromDB.isPresent()) {
            productRepository.save(product);
        }
    }

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
    public void createProductFromAddProductForm(ProductAddForm productAddForm, Authentication authentication) throws IOException, UserDoesNotExist {
        Product product = new Product();
        long id = this.productRepository.getMaxId();
        product.setId(++id);
        product.setProductName(productAddForm.getProductName());
        product.setSeller(this.userService.getUserFromAuthentication(authentication).orElseThrow(UserDoesNotExist::new));
        product.setCost(Integer.parseInt(productAddForm.getCost()));
        product.setQuantity(Integer.parseInt(productAddForm.getQuantity()));
        product.setDiscount(0);
        product.setDescription(productAddForm.getDescription());
        product.setDeleted(false);
        product.setDate(new Date());
        product.setCategory(this.categoryRepository.findByEnCategoryOrRuCategory(productAddForm.getMotherCategory(), productAddForm.getMotherCategory()).orElse(null));
        this.imageProductService.saveImage(productAddForm.getFiles(), id);
        this.productRepository.save(product);
    }

    @Override
    public Page<Product> getPageProduct(Pageable pageable) {
        return this.productRepository.findByDeleted(false, pageable);
    }

    @Override
    public Optional<Product> getProductById(long id) throws ProductDoesNotExist {
        Optional<Product> optionalProduct = productRepository.findByIdAndDeleted(id, false);
        if (!optionalProduct.isPresent()) {
            throw new ProductDoesNotExist();
        }
        return optionalProduct;
    }

    @Override
    public void deleteProduct(Long id) throws ProductDoesNotExist{
        Product product = this.getProductById(id).get();
        this.wishListRepository.deleteAllByProduct(product);
        this.orderService.deleteProductFromBasket(product);
        if (this.orderService.hasOrderWithProduct(product)){
            product.setDeleted(true);
            this.productRepository.save(product);
        } else {
            this.productRepository.delete(product);
        }
    }

    @Override
    public void changeProduct(ProductChangeForm productChangeForm, long id) throws ProductDoesNotExist, IOException {
        Optional<Product> optionalProduct = this.getProductById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setProductName(productChangeForm.getProductName());
            product.setDescription(productChangeForm.getDescription());
            product.setCost(Integer.parseInt(productChangeForm.getCost()));
            product.setCategory(this.categoryRepository.findByEnCategoryOrRuCategory(productChangeForm.getMotherCategory(), productChangeForm.getMotherCategory()).orElse(null));
            this.productRepository.save(product);
            this.imageProductService.saveImage(productChangeForm.getFiles(), id);
            this.mailService.sendProductChange(product);
        } else {
            throw new ProductDoesNotExist();
        }
    }

    @Override
    public void changeProductFromSeller(ProductChangeFormFromProfile productChangeFormFromProfile, long id) throws ProductDoesNotExist {
        Optional<Product> optionalProduct = this.getProductById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setQuantity(Integer.parseInt(productChangeFormFromProfile.getQuantity()));
            product.setDescription(productChangeFormFromProfile.getDescription());
            product.setDiscount(Integer.parseInt(productChangeFormFromProfile.getDiscount()));
            this.productRepository.save(product);
        } else {
            throw new ProductDoesNotExist();
        }
    }

    @Override
    public Page<Product> getTenProductsForNovelties() {
        return productRepository.findAllByDeleted(PageRequest.of(0, 10, Sort.Direction.DESC,
                "id"), false);
    }

    @Override
    public List<Product> getTenWithSale() {
        return productRepository.findTenProductsWithSale();
    }

    @Override
    public Set<Product> getTenWithRandomCategory() {
        Optional<Category> randomCategory;
        List<Product> productsWithCategoryFromDB;
        while (true) {
            randomCategory = categoryRepository.findRandomCategory();
            productsWithCategoryFromDB = productRepository.productWithMotherCategory(randomCategory.get().getCategoryId(),
                    PageRequest.of(0, 50)).getContent();
            if (productsWithCategoryFromDB.size() > 0) break;

        }
        Set<Product> productWithRandomCategory = new HashSet<>();
        int size = productsWithCategoryFromDB.size();
        if (size < 10) {
            for (int i = 0; i < size; i++) {
                productWithRandomCategory.add(productsWithCategoryFromDB.get(i));
            }
        } else {
            for (int i = 0; i < size; i++) {
                int number = (int) (Math.random() * (size));
                productWithRandomCategory.add(productsWithCategoryFromDB.get(number));
            }
        }
        return productWithRandomCategory;
    }

    @Override
    public Page<Product> getAllProductsForTheSeller(Pageable pageable, Authentication authentication) throws UserDoesNotExist {
        Optional<User> currentUser = userService.getUserFromAuthentication(authentication);
        if (currentUser.isPresent()) {
            return productRepository.findAllBySeller(currentUser.get(), pageable);
        } else {
            throw new UserDoesNotExist();
        }
    }

    @Override
    public Page<WishList> getWishLists(Pageable pageable, Authentication authentication) throws UserDoesNotExist {
        Optional<User> user = userService.getUserFromAuthentication(authentication);
        if (user.isPresent()) {
            return wishListRepository.findAllByUser(user.get(), pageable);
        } else {
            throw new UserDoesNotExist();
        }
    }

    @Override
    public void addProductToWishList(Authentication authentication, long id) throws ProductDoesNotExist, UserDoesNotExist {
        Product product = this.getProductById(id).get();
        User user = this.userService.getUserFromAuthentication(authentication).orElseThrow(UserDoesNotExist::new);
        if (wishListRepository.countByProductAndUser(product, user) == 0) {
            WishList wishList = new WishList();
            wishList.setProduct(product);
            wishList.setUser(user);
            wishListRepository.save(wishList);
        }
    }
}
