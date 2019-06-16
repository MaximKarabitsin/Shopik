package ru.sstu.shopik.controllers.adminPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.domain.entities.Product;
import ru.sstu.shopik.exceptions.ProductDoesNotExist;
import ru.sstu.shopik.forms.ProductChangeForm;
import ru.sstu.shopik.forms.validators.ProductAddFormValidator;
import ru.sstu.shopik.forms.validators.ProductChangeFormValidator;
import ru.sstu.shopik.services.ProductService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/adminpanel/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductChangeFormValidator productValidator;

    @InitBinder("productChangeForm")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(this.productValidator);
    }

    @ModelAttribute
    public void addCurrentPage(Model model) {
        model.addAttribute("currentSection", "products");
    }

    @GetMapping
    public String getProducts(@RequestParam(required = false) Integer page, Model model) {

        if (page == null) {
            page = 0;
        } else {
            page -= 1;
        }
        Page<Product> productPage = this.productService.getPageProduct(page);
        model.addAttribute("products", productPage);
        return "adminPanel/products";
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable("id") String string, @RequestParam(required = false) String delete, Model model, ProductChangeForm productChangeForm) {
        try {
            long id = Long.parseLong(string);
            if (delete != null) {
                this.productService.deleteProduct(id);
                return "redirect:/adminpanel/products";
            }
            Optional<Product> optionalProduct;
            optionalProduct = this.productService.getProductById(id);
            model.addAttribute("p", optionalProduct.orElse(null));
            model.addAttribute("productChangeForm", productChangeForm);
            return "adminPanel/product";
        } catch (NumberFormatException | ProductDoesNotExist e) {
            return "redirect:/adminpanel/products";
        }
    }

    @PostMapping("/{id}")
    public String changeProduct(@PathVariable Long id, Model model, @Valid @ModelAttribute("productChangeForm") ProductChangeForm productChangeForm,
                                BindingResult binding) {
        try {
            Optional<Product> optionalProduct = this.productService.getProductById(id);
            model.addAttribute("p", optionalProduct.orElse(null));
            if (binding.hasErrors()) {
                return "adminPanel/product";
            }
            this.productService.changeProduct(productChangeForm, id);
            return "redirect:/adminpanel/products/" + id;
        } catch (ProductDoesNotExist e) {
            return "redirect:/adminpanel/products";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }
}
