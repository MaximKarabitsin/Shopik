package ru.sstu.shopik.controllers.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.domain.entities.Product;
import ru.sstu.shopik.domain.models.Pager;
import ru.sstu.shopik.exceptions.ProductDoesNotExist;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.forms.ProductChangeFormFromProfile;
import ru.sstu.shopik.services.ProductService;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/profile/products")
public class SellerProductsController {

    private static final int INITIAL_PAGE_SIZE = 18;

    @Autowired
    private ProductService productService;

    @ModelAttribute
    public void addCurrentPage(Model model) {
        model.addAttribute("currentSection", "products");
    }

    @GetMapping()
    public String getAllProductsForSeller(Model model, @PageableDefault(size = INITIAL_PAGE_SIZE) Pageable pageable,
                                          Authentication authentication) {
        pageable = isCorrectPage(pageable);
        try {
            Page<Product> products = productService.getAllProductsForTheSeller(pageable, authentication);
            Pager pager = new Pager(products.getTotalPages(), products.getNumber());
            model.addAttribute("products", products);
            model.addAttribute("pager", pager);
            return "profile/allUserProducts";
        } catch (UserDoesNotExist e) {
            return "profile/profile";
        }

    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id, @RequestParam(required = false) String delete, Model model, ProductChangeFormFromProfile productChangeFormFromProfile) {
        if (delete != null) {
            this.productService.deleteProduct(id);
            return "redirect:/profile/products";
        }
        Optional<Product> optionalProduct = this.productService.getProductById(id);
        model.addAttribute("p", optionalProduct.orElse(null));
        model.addAttribute("productChangeFormFromProfile", productChangeFormFromProfile);
        return "profile/product";
    }

    @PostMapping("/{id}")
    public String editProduct(@PathVariable Long id, Model model, Locale locale, @Valid @ModelAttribute("productChangeFormFromProfile") ProductChangeFormFromProfile productChangeFormFromProfile,
                              BindingResult binding) {
        Optional<Product> optionalProduct = this.productService.getProductById(id);
        model.addAttribute("p", optionalProduct.orElse(null));
        if (binding.hasErrors()) {
            return "profile/product";
        }
        try {
            this.productService.changeProductFromSeller(productChangeFormFromProfile, id);
        } catch (ProductDoesNotExist e) {
        }
        return "redirect:/profile/products/" + id;

    }

    private Pageable isCorrectPage(Pageable pageable) {
        if (pageable.getPageSize() != INITIAL_PAGE_SIZE) {
            return PageRequest.of(0, INITIAL_PAGE_SIZE);
        } else {
            return pageable;
        }
    }
}
