package ru.sstu.shopik.controllers.adminPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sstu.shopik.forms.NewsAddForm;
import ru.sstu.shopik.services.NewsService;

import javax.validation.Valid;

@Controller
@RequestMapping("/adminpanel/addNews")
public class AddNewsController {

    @Autowired
    NewsService newsService;

    @ModelAttribute
    public void addCurrentPage(Model model) {
        model.addAttribute("currentSection", "news");
    }

    @PostMapping
    public String newProduct(Model model, @Valid @ModelAttribute("newsAddForm") NewsAddForm newsAddForm, BindingResult binding) {
        if (binding.hasErrors()) {
            return "adminPanel/addNews";
        }
        try {
            this.newsService.createNewsFromAddNewsForm(newsAddForm);
        } catch (Exception e) {
            return "redirect:/error";
        }
        return "/adminpanel/addNews";
    }

    @GetMapping
    public String addNews(Model model, NewsAddForm newsAddForm) {
        model.addAttribute("newsAddForm", newsAddForm);
        return "adminPanel/addNews";
    }
}
