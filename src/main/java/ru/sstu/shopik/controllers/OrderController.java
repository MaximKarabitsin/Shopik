package ru.sstu.shopik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.domain.entities.Order;
import ru.sstu.shopik.exceptions.ProductDoesNotExist;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.services.OrderService;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("basket")
    public String getBasket(Model model, Authentication authentication) {
        model.addAttribute("currentSection", "basket");
        try {
            model.addAttribute("order", this.orderService.getBasket(authentication));
        } catch (UserDoesNotExist e) {
        }
        return "order/basket";
    }

    @RequestMapping(value = "basket/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Boolean addInBasket(Long productId, Authentication authentication) {
        boolean response = true;
        try {
            this.orderService.addInBasket(productId, authentication);

        } catch (UserDoesNotExist | ProductDoesNotExist e) {
            response = false;
        }
        return response;

    }

    @RequestMapping(value = "/basket/changeQuantity", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Boolean changeQuantity(Integer quantity, Long productId, Authentication authentication) {
        boolean response = true;
        try {
            this.orderService.changeQuantity(quantity, productId, authentication);

        } catch (UserDoesNotExist | ProductDoesNotExist e) {
            response = false;
        }
        return response;

    }

    @GetMapping("/basket/{productId}")
    public String deleteProductBasket(@PathVariable Long productId, @RequestParam String delete, Model model, Authentication authentication) {
        try {
            this.orderService.deleteProduct(productId, authentication);

        } catch (UserDoesNotExist | ProductDoesNotExist e) {
        }
        return "redirect:/basket";
    }

    @GetMapping("/basket/order")
    public String createOrder(Model model, Authentication authentication) {
        model.addAttribute("currentSection", "order");
        try {
            Order order = this.orderService.createOrder(authentication);
            if (order != null) {
                model.addAttribute("message", "Заказ подтвержден!");
            } else {
                model.addAttribute("message", "Заказ не подтвержден! Проверьте товары их их колличество");
            }
        } catch (UserDoesNotExist e) {
        }
        return "order/order";
    }
}
