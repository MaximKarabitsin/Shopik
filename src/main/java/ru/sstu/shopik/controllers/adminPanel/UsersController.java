package ru.sstu.shopik.controllers.adminPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.domain.entities.User;
import ru.sstu.shopik.exceptions.InvalidCurrentPassword;
import ru.sstu.shopik.exceptions.InvalidLogin;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.forms.FullNameChangeForm;
import ru.sstu.shopik.forms.PasswordChangeForm;
import ru.sstu.shopik.forms.UserChangeForm;
import ru.sstu.shopik.forms.validators.UserChangeFormValidator;
import ru.sstu.shopik.services.UserService;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/adminpanel/users")
public class UsersController {

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    UserChangeFormValidator userValidator;

    @InitBinder("userChangeForm")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(this.userValidator);
    }

    @ModelAttribute
    public void addCurrentPage(Model model) {
        model.addAttribute("currentSection", "users");
    }

    @GetMapping
    public String getUsers(@RequestParam(required = false) Integer page, Model model) {
        if (page == null) {
            page = 0;
        } else {
            page -= 1;
        }
        Page<User> userPage = this.userService.getPageUser(page);
        int t = userPage.getTotalPages();
        model.addAttribute("users", userPage);

        return "adminPanel/users";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id, Model model, UserChangeForm userChangeForm) {
        Optional<User> optionalUser = this.userService.getUserById(id);
        model.addAttribute("u", optionalUser.orElse(null));
        model.addAttribute("userChangeForm", userChangeForm);
        return "adminPanel/user";
    }


    @PostMapping("/{id}")
    public String changeUser(@PathVariable Long id, Model model, Locale locale, @Valid @ModelAttribute("userChangeForm") UserChangeForm userChangeForm,
                             BindingResult binding) {
        Optional<User> optionalUser = this.userService.getUserById(id);
        model.addAttribute("u", optionalUser.orElse(null));
        if (binding.hasErrors()) {
            return "adminPanel/user";
        }

        try {
            this.userService.changeUser(userChangeForm, id);
        } catch (UserDoesNotExist e) {

        } catch (InvalidLogin e){
            model.addAttribute("errorLogin", messageSource.getMessage("enter.login.exist", null, locale));
            return "adminPanel/user";
        }
        return "redirect:/adminpanel/users/" + id;
    }
}
