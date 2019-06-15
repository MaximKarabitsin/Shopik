package ru.sstu.shopik.services;

import org.springframework.security.core.Authentication;
import ru.sstu.shopik.domain.entities.Order;
import ru.sstu.shopik.exceptions.ProductDoesNotExist;
import ru.sstu.shopik.exceptions.UserDoesNotExist;

public interface OrderService {
    void addInBasket(Long id, Authentication authentication) throws UserDoesNotExist, ProductDoesNotExist;
    Order getOrder(Authentication authentication) throws UserDoesNotExist;
}
