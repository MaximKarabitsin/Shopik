package ru.sstu.shopik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.exceptions.ProductDoesNotExist;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.services.OrderService;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "basket/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Boolean addInBasket(Long productId, Authentication authentication) {
        boolean response = true;
        try {
            orderService.addInBasket(productId, authentication);

        } catch (UserDoesNotExist | ProductDoesNotExist e) {
            response = false;
        }
        return response;

    }

    @GetMapping("basket")
    public String getBasket(Model model, Authentication authentication) {
        model.addAttribute("currentSection", "basket");
        try {
            model.addAttribute("order", this.orderService.getOrder(authentication));
        } catch (UserDoesNotExist e) {
        }
        return "order/basket";
    }
}
