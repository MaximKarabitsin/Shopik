package ru.sstu.shopik.controllers.adminPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sstu.shopik.domain.entities.News;
import ru.sstu.shopik.services.NewsService;

@Controller
@RequestMapping("/adminpanel/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    @ModelAttribute
    public void addCurrentPage(Model model) {
        model.addAttribute("currentSection", "news");
    }

    @GetMapping
    public String getNews(@RequestParam(required = false) Integer page, Model model) {

        if (page == null) {
            page = 0;
        } else {
            page -= 1;
        }
        Page<News> newsPage = this.newsService.getPageProduct(page);
        model.addAttribute("news", newsPage);
        return "adminPanel/news";
    }
}
