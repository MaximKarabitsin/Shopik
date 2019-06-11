package ru.sstu.shopik.controllers.adminPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.domain.entities.User;
import ru.sstu.shopik.exceptions.InvalidCurrentPassword;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.forms.FullNameChangeForm;
import ru.sstu.shopik.forms.PasswordChangeForm;
import ru.sstu.shopik.services.UserService;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/adminpanel")
public class CategoriesController {

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @ModelAttribute
    public void addCurrentPage(Model model) {
        model.addAttribute("currentSection", "users");
    }

    @GetMapping
    public String getProfile(@RequestParam(required = false) Integer page, Model model) {
        if (page == null) {
            page = 0;
        } else {
            page-=1;
        }
        Page<User> userPage = this.userService.getPageUser(page);
int t = userPage.getTotalPages();
        model.addAttribute("users", userPage);

        return "adminPanel/users";
    }


    @PostMapping("/fullNameChange")
    public String changeFullName(Model model, Authentication authentication, @Valid @ModelAttribute("fullNameChangeForm") FullNameChangeForm fullNameChangeForm,
                                 BindingResult binding) {
        if (binding.hasErrors()) {
            return "profile/profile";
        }
        try {
            this.userService.changeUserFullName(authentication, fullNameChangeForm);
            return "redirect:/profile";
        } catch (UserDoesNotExist e) {
            return "redirect:/error";
        }
    }

    @PostMapping("/passwordChange")
    public String changePassword(Model model, Locale locale, Authentication authentication, @Valid @ModelAttribute("passwordChangeForm") PasswordChangeForm passwordChangeForm,
                                 BindingResult binding) {
        if (binding.hasErrors()) {
            return "profile/profile";
        }
        try {
            this.userService.changeUserPassword(authentication, passwordChangeForm);
        } catch (InvalidCurrentPassword e) {
            model.addAttribute("errorChangePassword", this.messageSource.getMessage("settings.section.profile.currentPassword.invalid", null, locale));
            return "profile/profile";
        } catch (UserDoesNotExist e) {
            return "redirect:/error";
        }

        return "redirect:/profile";
    }

}
