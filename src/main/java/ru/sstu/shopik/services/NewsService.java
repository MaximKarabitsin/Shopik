package ru.sstu.shopik.services;

import org.springframework.data.domain.Page;
import ru.sstu.shopik.domain.entities.News;
import ru.sstu.shopik.forms.NewsAddForm;

import java.io.IOException;

public interface NewsService {
    Page<News> getPageProduct(int page);

    void createNewsFromAddNewsForm(NewsAddForm newsAddForm) throws IOException;

    Page<News> getTenNewsForNews();
}
