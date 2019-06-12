package ru.sstu.shopik.controllers.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.domain.entities.Category;
import ru.sstu.shopik.exceptions.CategoryDoesNotExist;
import ru.sstu.shopik.forms.FullNameChangeForm;
import ru.sstu.shopik.forms.PasswordChangeForm;
import ru.sstu.shopik.forms.ProductAddForm;
import ru.sstu.shopik.services.CategoryService;
import ru.sstu.shopik.services.ProductService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("profile/products/addProduct")
public class AddProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;


    @ModelAttribute
    public void addCurrentPage(Model model) {
        model.addAttribute("currentSection", "products");
    }

    @PostMapping
    public String newProduct(Model model, @Valid @ModelAttribute("productAddForm") ProductAddForm productAddForm, BindingResult binding) {
        if (binding.hasErrors()) {
            model.addAttribute("catalog", categoryService.getCatalog().orElse(null));
            return "profile/addProduct";
        }
        this.productService.createProductFromAddProductForm(productAddForm);
        return "redirect:/profile/products/addProduct";
    }

    @GetMapping
    public String addProduct(Model model, ProductAddForm productAddForm) {
        model.addAttribute("productAddForm", productAddForm);
        model.addAttribute("catalog", categoryService.getCatalog().orElse(null));
        return "profile/addProduct";
    }

    @RequestMapping("/categoryMain")
    @ResponseBody
    public List<Category> getCatalog(@RequestParam("name") String name, Model model) {
        try {
            return categoryService.getSubCategories(name);
        } catch (CategoryDoesNotExist ex) {
            return null;
        }
    }
}
