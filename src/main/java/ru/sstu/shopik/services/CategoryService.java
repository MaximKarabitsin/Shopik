package ru.sstu.shopik.services;

import ru.sstu.shopik.domain.entities.Category;

import java.util.Optional;

public interface CategoryService {
    Optional<Category> getCatalog();
    Optional<Category> getCategoryById(int id);
}
