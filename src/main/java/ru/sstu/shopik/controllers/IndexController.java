package ru.sstu.shopik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.domain.entities.Category;
import ru.sstu.shopik.domain.entities.Product;
import ru.sstu.shopik.services.CategoryService;
import ru.sstu.shopik.services.ProductService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private ProductService productService;

    @ModelAttribute
    public void addCurrentPage(Model model) {
        model.addAttribute("currentPage", "home");
    }


    @GetMapping()
    public String getProductsForNovelties(Model model) {

        model.addAttribute("listOfTenForNovelties", productService.getTenProductsForNovelties());
        model.addAttribute("listOfTenForSales", productService.getTenWithSale());
        Set<Product> listWithRandomCategory = productService.getTenWithRandomCategory();
        Category randomMotherCategory = listWithRandomCategory.iterator().next().getCategory().getMotherCategory();
        model.addAttribute("listFromRandomCategory", listWithRandomCategory);
        model.addAttribute("motherCategory", randomMotherCategory);
        return "index";
    }

    @GetMapping("/{productId}")
    public String addProductToWishList(@PathVariable long productId, Authentication authentication) {
        productService.addProductToWishList(authentication, productId);
        return "redirect:/";
    }

}
