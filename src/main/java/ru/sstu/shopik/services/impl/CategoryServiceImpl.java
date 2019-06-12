package ru.sstu.shopik.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sstu.shopik.dao.CategoryRepository;
import ru.sstu.shopik.domain.entities.Category;
import ru.sstu.shopik.exceptions.CategoryDoesNotExist;
import ru.sstu.shopik.services.CategoryService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public Optional<Category> getCatalog() {

        return this.categoryRepository.findByEnCategory("Catalog");
    }

    @Override
    public List<Category> getSubCategories(String categoryName) throws CategoryDoesNotExist {
        Optional<Category> optionalCategory = this.categoryRepository.findByEnCategoryOrRuCategory(categoryName, categoryName);
        if (optionalCategory.isPresent() && optionalCategory.orElse(null).getMotherCategory().getCategoryId() == 2) {
            return Objects.requireNonNull(optionalCategory.orElse(null)).getSubCategories();
        } else {
            throw new CategoryDoesNotExist();
        }
    }
}
