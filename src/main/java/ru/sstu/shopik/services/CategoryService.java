package ru.sstu.shopik.services;

import ru.sstu.shopik.domain.entities.Category;

import java.util.Optional;

public interface CategoryService {
    public Optional<Category> getCatalog();
}
