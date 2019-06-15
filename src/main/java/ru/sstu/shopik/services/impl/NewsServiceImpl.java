package ru.sstu.shopik.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.sstu.shopik.dao.NewsRepository;
import ru.sstu.shopik.domain.entities.News;
import ru.sstu.shopik.forms.NewsAddForm;
import ru.sstu.shopik.services.NewsService;

import java.io.IOException;
import java.util.Date;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsRepository newsRepository;

    @Override
    public Page<News> getPageProduct(int page) {
        return this.newsRepository.findAll(PageRequest.of(page, 10));
    }

    @Override
    public void createNewsFromAddNewsForm(NewsAddForm newsAddForm) throws IOException {
        News news = new News();
        BeanUtils.copyProperties(newsAddForm, news);
        news.setDate(new Date());
        this.newsRepository.save(news);
    }

    @Override
    public Page<News> getTenNewsForNews() {
        return newsRepository.findAll(PageRequest.of(0, 8, Sort.Direction.DESC, "newsId"));
    }
}
