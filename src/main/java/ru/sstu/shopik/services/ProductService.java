package ru.sstu.shopik.services;

import ru.sstu.shopik.forms.ProductAddForm;

import java.io.IOException;

public interface ProductService {
    void createProductFromAddProductForm(ProductAddForm productAddForm) throws IOException;
}
