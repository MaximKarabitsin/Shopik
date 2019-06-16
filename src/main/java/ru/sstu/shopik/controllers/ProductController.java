package ru.sstu.shopik.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sstu.shopik.domain.entities.Product;
import ru.sstu.shopik.exceptions.ProductDoesNotExist;
import ru.sstu.shopik.services.impl.ProductServiceImpl;

import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/{productId}")
    public String getInfoAboutProduct(@PathVariable String  productId, Model model) {
        try {
            long id = Long.parseLong(productId);
            Optional<Product> product = productService.getInfoAboutProductForBigPageById(id);
            if (product.isPresent()) {
                model.addAttribute("product", product.get());
                return "catalog/product";
            } else {
                throw new ProductDoesNotExist();
            }
        } catch (NumberFormatException | ProductDoesNotExist e) {
            return "redirect:/";
        }
    }
}
