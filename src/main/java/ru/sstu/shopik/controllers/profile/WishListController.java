package ru.sstu.shopik.controllers.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.domain.entities.WishList;
import ru.sstu.shopik.domain.models.Pager;
import ru.sstu.shopik.exceptions.ProductDoesNotExist;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.services.ProductService;


@Controller
@RequestMapping("/wishlist")
public class WishListController {

    private static final int INITIAL_PAGE_SIZE = 10;

    @Autowired
    private ProductService productService;

    @ModelAttribute
    public void addCurrentPage(Model model) {
        model.addAttribute("currentSection", "wish");
    }

    @GetMapping()
    public String getWishList(Model model, @PageableDefault(size = INITIAL_PAGE_SIZE) Pageable pageable, Authentication authentication) {
        pageable = isCorrectPage(pageable);
        try {
            Page<WishList> products = productService.getWishLists(pageable, authentication);
            Pager pager = new Pager(products.getTotalPages(), products.getNumber());
            model.addAttribute("wishLists", products);
            model.addAttribute("pager", pager);
            return "profile/wishList";
        } catch (UserDoesNotExist e) {
            return "index";
        }

    }

    @GetMapping("/{productId}")
    public String deleteList(@PathVariable Long productId, @RequestParam String delete, Authentication authentication){
        try {
            this.productService.deleteProductFromWishList(productId, authentication);
        } catch (ProductDoesNotExist e) {
        }
        return "redirect:/wishlist";
    }

    @RequestMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Boolean addInBasket(Long productId, Authentication authentication) {
        boolean response = true;
        try {
            productService.addProductToWishList(authentication, productId);

        } catch (UserDoesNotExist | ProductDoesNotExist e) {
            response = false;
        }
        return response;

    }


    private Pageable isCorrectPage(Pageable pageable) {
        if (pageable.getPageSize() != INITIAL_PAGE_SIZE) {
            return PageRequest.of(0, INITIAL_PAGE_SIZE);
        } else {
            return pageable;
        }
    }
}
