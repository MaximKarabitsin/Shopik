package ru.sstu.shopik.services;

import ru.sstu.shopik.domain.entities.Category;
import ru.sstu.shopik.exceptions.CategoryDoesNotExist;
import ru.sstu.shopik.forms.CategoryAddForm;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> getCatalog();
    Optional<Category> getCategoryById(int id);
    void addCategory(CategoryAddForm categoryAddForm);
    boolean checkMotherCategory(int motherId);
    List<Category> getSubCategories(String motherName) throws CategoryDoesNotExist;
    Optional<Category> getCategoryByEnCategory(String enCategory);
}
