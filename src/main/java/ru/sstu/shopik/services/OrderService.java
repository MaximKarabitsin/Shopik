package ru.sstu.shopik.services;

import org.springframework.security.core.Authentication;
import ru.sstu.shopik.domain.entities.Order;
import ru.sstu.shopik.domain.entities.Product;
import ru.sstu.shopik.exceptions.ProductDoesNotExist;
import ru.sstu.shopik.exceptions.UserDoesNotExist;

public interface OrderService {
    void addInBasket(Long id, Authentication authentication) throws UserDoesNotExist, ProductDoesNotExist;

    Order getBasket(Authentication authentication) throws UserDoesNotExist;

    Order createOrder(Authentication authentication) throws UserDoesNotExist;

    void changeQuantity(Integer quantity, Long productId, Authentication authentication) throws UserDoesNotExist, ProductDoesNotExist;

    void deleteProduct(Long productId, Authentication authentication) throws UserDoesNotExist, ProductDoesNotExist;

    void deleteProductFromBasket(Product product);

    boolean hasOrderWithProduct(Product product);
}
