package ru.sstu.shopik.controllers.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sstu.shopik.exceptions.InvalidCurrentPassword;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.forms.FullNameChangeForm;
import ru.sstu.shopik.forms.PasswordChangeForm;
import ru.sstu.shopik.services.UserService;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @ModelAttribute
    public void addCurrentPage(Model model, FullNameChangeForm fullNameChangeForm, PasswordChangeForm passwordChangeForm) {
        model.addAttribute("fullNameChangeForm", fullNameChangeForm);
        model.addAttribute("passwordChangeForm", passwordChangeForm);
        model.addAttribute("currentSection", "profile");
    }

    @GetMapping
    public String getProfile(Model model) {
        return "profile/profile";
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
