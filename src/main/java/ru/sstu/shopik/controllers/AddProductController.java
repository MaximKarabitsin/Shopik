package ru.sstu.shopik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.domain.entities.Category;
import ru.sstu.shopik.exceptions.CategoryDoesNotExist;
import ru.sstu.shopik.forms.ProductAddForm;
import ru.sstu.shopik.forms.responses.JsonResponse;
import ru.sstu.shopik.services.CategoryService;
import ru.sstu.shopik.services.ProductServiceImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/addProduct")
public class AddProductController {

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    CategoryService categoryService;

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public JsonResponse newProduct(@ModelAttribute @Valid ProductAddForm productAddForm, BindingResult result) {

        JsonResponse jsonResponse = new JsonResponse();
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(
                            Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
                    );
            jsonResponse.setValidated(false);
            jsonResponse.setErrorMessages(errors);
        } else {

            productService.createProductFromAddProductForm(productAddForm);
            jsonResponse.setValidated(true);
        }
        return jsonResponse;
    }

    @GetMapping
    public String addProduct(Model model) {
        model.addAttribute("catalog", categoryService.getCatalog().orElse(null));
        return "addProduct";
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
