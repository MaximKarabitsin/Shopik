package ru.sstu.shopik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sstu.shopik.services.CategoryService;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    CategoryService categoryService;

    @ModelAttribute
    public void addCurrentPage(Model model) {
        model.addAttribute("currentPage", "catalog");
    }

    @GetMapping
    public String getCatalog(Model model) {
        model.addAttribute("catalog", this.categoryService.getCatalog().orElse(null));
        return "catalog/catalog";
    }
}
