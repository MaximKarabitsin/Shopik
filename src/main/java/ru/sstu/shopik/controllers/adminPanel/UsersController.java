package ru.sstu.shopik.controllers.adminPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.sstu.shopik.domain.entities.User;
import ru.sstu.shopik.domain.models.Pager;
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
    private static final int INITIAL_PAGE_SIZE = 10;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserChangeFormValidator userValidator;

    @InitBinder("userChangeForm")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(this.userValidator);
    }

    @ModelAttribute
    public void addCurrentPage(Model model) {
        model.addAttribute("currentSection", "users");
    }

    @GetMapping
    public String getUsers(@PageableDefault(size = INITIAL_PAGE_SIZE) Pageable pageable, Model model) {
        pageable = isCorrectPage(pageable);
        Page<User> userPage = this.userService.getPageUser(pageable);
        Pager pager = new Pager(userPage.getTotalPages(), userPage.getNumber());

        model.addAttribute("users", userPage);
        model.addAttribute("pager", pager);

        return "adminPanel/users";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable String id, @RequestParam(required = false) String delete, Model model, UserChangeForm userChangeForm) {
        try {
            long userId = Long.parseLong(id);
            if (delete != null) {
                this.userService.deleteUser(userId);
                return "redirect:/adminpanel/users";
            }
            Optional<User> optionalUser = this.userService.getUserById(userId);
            model.addAttribute("u", optionalUser.orElse(null));
            model.addAttribute("userChangeForm", userChangeForm);
            return "adminPanel/user";
        } catch (NumberFormatException e) {
            return "redirect:/adminpanel/users/";
        }
    }


    @PostMapping("/{id}")
    public String changeUser(@PathVariable String id, Model model, Locale locale, @Valid @ModelAttribute("userChangeForm") UserChangeForm userChangeForm,
                             BindingResult binding) {
        try {
            long userId = Long.parseLong(id);
            Optional<User> optionalUser = this.userService.getUserById(userId);
            model.addAttribute("u", optionalUser.orElse(null));
            if (binding.hasErrors()) {
                return "adminPanel/user";
            }
            this.userService.changeUser(userChangeForm, userId);
            return "redirect:/adminpanel/users/" + userId;

        } catch (NumberFormatException | UserDoesNotExist e) {
            return "redirect:/adminpanel/users/";
        } catch (InvalidLogin e) {
            model.addAttribute("errorLogin", messageSource.getMessage("enter.login.exist", null, locale));
            return "adminPanel/user";
        }

    }

    private Pageable isCorrectPage(Pageable pageable) {
        if (pageable.getPageSize() != INITIAL_PAGE_SIZE) {
            return PageRequest.of(0, INITIAL_PAGE_SIZE);
        } else {
            return pageable;
        }
    }
}
