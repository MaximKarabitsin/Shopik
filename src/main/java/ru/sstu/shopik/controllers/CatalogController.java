package ru.sstu.shopik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sstu.shopik.domain.entities.Category;
import ru.sstu.shopik.services.CategoryService;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public String getCatalog(Model model) {

        model.addAttribute("catalog",categoryService.getCatalog().orElse(null));
        return "catalog";
    }
}
