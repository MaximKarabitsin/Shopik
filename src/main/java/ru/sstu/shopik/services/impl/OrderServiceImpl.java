package ru.sstu.shopik.services.impl;

import org.hibernate.collection.internal.PersistentBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.sstu.shopik.dao.*;
import ru.sstu.shopik.domain.entities.*;
import ru.sstu.shopik.exceptions.ProductDoesNotExist;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.services.MailService;
import ru.sstu.shopik.services.OrderService;
import ru.sstu.shopik.services.UserService;

import java.util.*;

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Override
    public void addInBasket(Long id, Authentication authentication) throws UserDoesNotExist, ProductDoesNotExist {
        Optional<User> optionalUser = this.userService.getUserFromAuthentication(authentication);
        User buyer = optionalUser.orElseThrow(UserDoesNotExist::new);
        Order order = this.getBasket(buyer);
        List<OrderProduct> list = order.getOrderProducts();
        Product product = this.productRepository.findById(id).orElseThrow(ProductDoesNotExist::new);
        if (product.getQuantity() == 0) {
            return;
        }
        OrderProduct orderProduct = this.getOrderProduct(order, product);
        orderProduct.setCost(product.getCostWithDiscount());
        orderProduct.setQuantity(1);
        list.add(orderProduct);
        order.setOrderProducts(list);
        this.orderRepository.save(order);
    }

    private Order getBasket(User buyer) {
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
    public Order getBasket(Authentication authentication) throws UserDoesNotExist {
        Optional<User> optionalUser = this.userService.getUserFromAuthentication(authentication);
        User buyer = optionalUser.orElseThrow(UserDoesNotExist::new);
        return this.getBasket(buyer);
    }

    @Override
    public Order createOrder(Authentication authentication) throws UserDoesNotExist {
        Order order = this.getBasket(authentication);
        List<OrderProduct> orderProducts = order.getOrderProducts();
        User buyer = order.getBuyer();
        if (orderProducts.isEmpty()) {
            return null;
        }
        int cost = this.checkQuantityAndCost(orderProducts, buyer);
        if (cost != -1) {
            Map<User, Set<OrderProduct>> sellerProducts = new HashMap<>();
            Map<User, Long> sellerCost = new HashMap<>();
            for (OrderProduct orderProduct : orderProducts) {
                Product product = orderProduct.getProduct();
                int quantity = product.getQuantity() - orderProduct.getQuantity();
                product.setQuantity(quantity);
                this.productRepository.save(product);
                Set<OrderProduct> orderProductSet = sellerProducts.get(product.getSeller());
                if (orderProductSet == null) {
                    orderProductSet = new HashSet<>();
                }
                orderProductSet.add(orderProduct);
                sellerProducts.put(product.getSeller(), orderProductSet);

                Long sCost = sellerCost.get(product.getSeller());
                if (sCost == null) {
                    sCost = Long.valueOf(0);
                }
                sCost += orderProduct.getQuantity() * orderProduct.getCost();
                sellerCost.put(product.getSeller(), sCost);
            }
            order.setStatus(this.orderStatusRepository.findByStatus("purchased"));
            order.setDate(new Date());
            this.orderRepository.save(order);
            buyer.setBalance(buyer.getBalance() - cost);
            this.userRepository.save(buyer);
            mailService.sendOrderBuyer(order, cost);
            mailService.sendOrderSellers(sellerProducts, sellerCost, buyer);
            return order;
        } else {
            return null;
        }

    }

    /*
     * if (quantity > product.quantity) or (finalCost > buyer.balance)
     *  return -1
     * else
     *  return finalCost
     */
    private int checkQuantityAndCost(List<OrderProduct> orderProducts, User buyer) {
        boolean flag = true;
        int cost = 0;
        for (OrderProduct orderProduct : orderProducts) {
            if (orderProduct.getProduct().getQuantity() == 0) {
                flag = false;
                this.orderProductRepository.delete(orderProduct);
            } else {
                if (orderProduct.getQuantity() > orderProduct.getProduct().getQuantity()) {
                    flag = false;
                    orderProduct.setQuantity(orderProduct.getProduct().getQuantity());
                    this.orderProductRepository.save(orderProduct);
                } else {
                    cost += orderProduct.getFinalCost();
                }
            }
        }
        if (!flag || buyer.getBalance() < cost) {
            cost = -1;
        }
        return cost;
    }

    @Override
    public void changeQuantity(Integer quantity, Long productId, Authentication authentication) throws
            UserDoesNotExist, ProductDoesNotExist {
        Product product = this.productRepository.findById(productId).orElseThrow(ProductDoesNotExist::new);
        OrderProduct orderProduct = this.getOrderProduct(product, authentication);
        if (product.getQuantity() >= quantity) {
            orderProduct.setQuantity(quantity);
            this.orderProductRepository.save(orderProduct);
        } else {
            orderProduct.setQuantity(product.getQuantity());
            this.orderProductRepository.save(orderProduct);
            throw new ProductDoesNotExist();
        }
    }

    @Override
    public void deleteProduct(Long productId, Authentication authentication) throws
            UserDoesNotExist, ProductDoesNotExist {
        Product product = this.productRepository.findById(productId).orElseThrow(ProductDoesNotExist::new);
        OrderProduct orderProduct = this.getOrderProduct(product, authentication);
        this.orderProductRepository.delete(orderProduct);
    }

    private OrderProduct getOrderProduct(Product product, Authentication authentication) throws
            UserDoesNotExist, ProductDoesNotExist {
        Optional<User> optionalUser = this.userService.getUserFromAuthentication(authentication);
        User buyer = optionalUser.orElseThrow(UserDoesNotExist::new);
        OrderStatus status = this.orderStatusRepository.findByStatus("basket");
        Optional<OrderProduct> optionalOrderProduct = this.orderProductRepository.findByStatusAndBuyerAndProduct(status, buyer, product);
        return optionalOrderProduct.orElseThrow(ProductDoesNotExist::new);
    }
}
