package ru.sstu.shopik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.domain.entities.Category;
import ru.sstu.shopik.domain.entities.Product;
import ru.sstu.shopik.exceptions.ProductDoesNotExist;
import ru.sstu.shopik.services.NewsService;
import ru.sstu.shopik.services.ProductService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private ProductService productService;

    @Autowired
    private NewsService newsService;

    @ModelAttribute
    public void addCurrentPage(Model model) {
        model.addAttribute("currentPage", "home");
    }


    @GetMapping()
    public String getProductsForNovelties(Model model) {
        model.addAttribute("listOfTenForNovelties", productService.getTenProductsForNovelties());
        model.addAttribute("listOfTenForSales", productService.getTenWithSale());
        List<Product> listWithRandomCategory = productService.getTenWithRandomCategory();
        model.addAttribute("listFromRandomCategory", listWithRandomCategory);
        model.addAttribute("category", listWithRandomCategory.get(0).getCategory());
        model.addAttribute("listOfTenNews", newsService.getTenNewsForNews());
        return "index";
    }
}
