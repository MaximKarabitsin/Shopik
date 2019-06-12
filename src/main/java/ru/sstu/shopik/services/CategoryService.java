package ru.sstu.shopik.services;

import ru.sstu.shopik.domain.entities.Category;
import ru.sstu.shopik.exceptions.CategoryDoesNotExist;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public Optional<Category> getCatalog();

    List<Category> getSubCategories(String motherName) throws CategoryDoesNotExist;
}
