package ru.sstu.shopik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sstu.shopik.services.CategoryService;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class IndexController {

    @ModelAttribute
    public void addCurrentPage(Model model) {
        model.addAttribute("currentPage", "home");
    }

    @GetMapping
    public String getIndex(Model model) {
        return "index";
    }
}
