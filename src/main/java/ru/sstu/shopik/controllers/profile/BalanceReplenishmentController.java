package ru.sstu.shopik.controllers.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.forms.BalanceReplenishmentForm;
import ru.sstu.shopik.services.UserService;

import javax.validation.Valid;

@Controller
public class BalanceReplenishmentController {

    @Autowired
    UserService userService;

    @GetMapping("/profile/replenishment")
    public String balanceReplenishment() {return "profile/replenishment";}

    @ModelAttribute
    public void addCurrentPage(Model model, BalanceReplenishmentForm balanceReplenishmentForm) {
        model.addAttribute("balanceReplenishmentForm", balanceReplenishmentForm);
        model.addAttribute("currentSection", "profile");
    }

    @PostMapping("/profile/replenishment")
    public String replenishmentOfBalance(Model model, @Valid @ModelAttribute("balanceReplenishmentForm") BalanceReplenishmentForm balanceReplenishmentForm,
                                         BindingResult binding, Authentication authentication) {
        if (binding.hasErrors()) {
            return "profile/replenishment";
        }
        try {
            this.userService.balanceReplenishment(balanceReplenishmentForm, authentication);
            return "profile/replenishment";
        } catch (UserDoesNotExist e) {
            return "redirect:/error";
        }

    }
}
