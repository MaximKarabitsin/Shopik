package ru.sstu.shopik.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sstu.shopik.dao.CategoryRepository;
import ru.sstu.shopik.domain.entities.Category;
import ru.sstu.shopik.services.CategoryService;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public Optional<Category> getCatalog() {

        return categoryRepository.findByEnCategory("Catalog");
    }

}
