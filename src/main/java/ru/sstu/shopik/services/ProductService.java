package ru.sstu.shopik.services;

import ru.sstu.shopik.forms.ProductAddForm;

public interface ProductService {
    void createProductFromAddProductForm(ProductAddForm productAddForm);
}
