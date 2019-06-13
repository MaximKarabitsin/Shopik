package ru.sstu.shopik.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sstu.shopik.domain.entities.Product;
import ru.sstu.shopik.services.impl.ProductServiceImpl;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/{productId}")
    public String getInfoAboutProduct(@PathVariable Long productId, Model model) {
        Product product = productService.getInfoAboutProductForBigPageById(productId).get();
        model.addAttribute("product", product);
        return "catalog/product";
    }
}
