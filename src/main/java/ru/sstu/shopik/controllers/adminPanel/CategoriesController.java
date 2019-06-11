package ru.sstu.shopik.controllers.adminPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.domain.entities.User;
import ru.sstu.shopik.exceptions.InvalidCurrentPassword;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.forms.CategoryAddForm;
import ru.sstu.shopik.forms.FullNameChangeForm;
import ru.sstu.shopik.forms.PasswordChangeForm;
import ru.sstu.shopik.services.CategoryService;
import ru.sstu.shopik.services.UserService;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/adminpanel/categories")
public class CategoriesController {


    @Autowired
    CategoryService categoryService;

    @ModelAttribute
    public void addCurrentPage(Model model) {
        model.addAttribute("currentSection", "categories");
    }

    @GetMapping
    public String getProfile(Model model, CategoryAddForm categoryAddForm) {
        model.addAttribute("categoryAddForm", categoryAddForm);
        model.addAttribute("catalog", this.categoryService.getCatalog().orElse(null));
        return "adminPanel/categories";
    }


    @PostMapping
    public String changeFullName(Model model, @Valid @ModelAttribute("categoryAddForm") CategoryAddForm categoryAddMain,
                                 BindingResult binding) {
        if (binding.hasErrors()) {
            return "adminPanel/categories";
        }
        return "redirect:/adminpanel/categories";
    }


}
