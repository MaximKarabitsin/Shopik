package ru.sstu.shopik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.sstu.shopik.services.ImageProductService;

@Controller
@RequestMapping("image/product")
public class ImageProductController {

    @Autowired
    private ImageProductService imageProductService;

    @GetMapping(value="main/{id}", produces= MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public FileSystemResource mainImage(Model model, @PathVariable Long id) {
        return this.imageProductService.getMainImage(id);
    }

    @GetMapping(value="small/{id}/{number}", produces= MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public FileSystemResource smallImage(Model model, @PathVariable Long id, @PathVariable int number) {
        return this.imageProductService.getSmallImage(id, number);
    }

    @GetMapping(value="big/{id}/{number}", produces= MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public FileSystemResource bigImage(Model model, @PathVariable Long id, @PathVariable int number) {
        return this.imageProductService.getBigImage(id, number);
    }
}
