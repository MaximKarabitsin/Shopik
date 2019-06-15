package ru.sstu.shopik.services.impl;

import org.hibernate.collection.internal.PersistentBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.sstu.shopik.dao.OrderProductRepository;
import ru.sstu.shopik.dao.OrderRepository;
import ru.sstu.shopik.dao.OrderStatusRepository;
import ru.sstu.shopik.dao.ProductRepository;
import ru.sstu.shopik.domain.entities.*;
import ru.sstu.shopik.exceptions.ProductDoesNotExist;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.services.OrderService;
import ru.sstu.shopik.services.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Override
    public void addInBasket(Long id, Authentication authentication) throws UserDoesNotExist, ProductDoesNotExist {
        Optional<User> optionalUser = this.userService.getUserFromAuthentication(authentication);
        User buyer = optionalUser.orElseThrow(UserDoesNotExist::new);
        Order order = this.getOrder(buyer);
        List<OrderProduct> list = order.getOrderProducts();
        Product product = this.productRepository.findById(id).orElseThrow(ProductDoesNotExist::new);
        OrderProduct orderProduct = this.getOrderProduct(order,product);
        orderProduct.setCost(product.getCostWithDiscount());
        orderProduct.setQuantity(1);
        list.add(orderProduct);
        order.setOrderProducts(list);
        this.orderRepository.save(order);
    }

    private Order getOrder(User buyer) {
        OrderStatus basketStatus = this.orderStatusRepository.findByStatus("basket");
        Optional<Order> orderOptional = this.orderRepository.findByBuyerAndStatus(buyer, basketStatus);
        Order order;
        if (!orderOptional.isPresent()) {
            order = new Order();
            order.setDate(new Date());
            order.setBuyer(buyer);
            order.setStatus(basketStatus);
            order.setOrderProducts(new ArrayList<>());
            this.orderRepository.save(order);
        } else {
            order = orderOptional.get();
        }
        return order;
    }

    private OrderProduct getOrderProduct(Order order, Product product) {
        Optional<OrderProduct> optionalOrderProduct = this.orderProductRepository.findByOrderAndProduct(order, product);
        OrderProduct orderProduct;
        if (!optionalOrderProduct.isPresent()) {
            orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);

        } else {
            orderProduct = optionalOrderProduct.get();
        }

        return orderProduct;
    }

    @Override
    public Order getOrder(Authentication authentication) throws UserDoesNotExist {
        Optional<User> optionalUser = this.userService.getUserFromAuthentication(authentication);
        User buyer = optionalUser.orElseThrow(UserDoesNotExist::new);
        return  this.getOrder(buyer);
    }
}
