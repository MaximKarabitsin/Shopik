package ru.sstu.shopik.services.impl;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sstu.shopik.dao.RoleRepository;
import ru.sstu.shopik.dao.UserRepository;
import ru.sstu.shopik.domain.UserDetailsImpl;
import ru.sstu.shopik.domain.entities.Role;
import ru.sstu.shopik.domain.entities.User;
import ru.sstu.shopik.forms.FullNameChangeForm;
import ru.sstu.shopik.forms.PasswordRecoveryForm;
import ru.sstu.shopik.forms.UserRegistrationForm;
import ru.sstu.shopik.services.MailService;
import ru.sstu.shopik.services.UserService;
import ru.sstu.shopik.utils.RandomStringUtil;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    MailService mailService;

    @Override
    public boolean isUserWithLoginExist(String login) {
        return this.userRepository.countByLogin(login) != 0 ? true : false;
    }

    @Override
    public boolean isUserWithEmailExist(String email) {
        return this.userRepository.countByEmail(email) != 0 ? true : false;
    }

    @Override
    public boolean isUserWithEmailExistAndEnabled(String email) {
        return this.userRepository.countByEnabledAndEmail(true, email) != 0 ? true : false;
    }

    @Override
    public void createUserFromRegistrationForm(UserRegistrationForm userForm, Locale locale) {
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(this.roleRepository.findByRole("USER"));
        if (userForm.getRole().equals("seller")) {
            roles.add(this.roleRepository.findByRole("SELLER"));
        }
        user.setRoles(roles);
        user.setBalance(0);
        user.setDate(new Date());
        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (this.userRepository.countByToken(token) != 0);
        user.setToken(token);
        user.setEnabled(false);
        this.userRepository.save(user);
        this.mailService.sendConfirmEmail(user, locale);
    }

    @Override
    public boolean confirmEmail(String token) {
        Optional<User> optionalUser = userRepository.findByToken(token);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            this.userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void recoverPassword(PasswordRecoveryForm passwordRecoveryForm, Locale locale) {
        Optional<User> optionalUser = this.userRepository.findByEmail(passwordRecoveryForm.getEmail());
        User user = optionalUser.get();
        String newPassword = RandomStringUtil.generateString(10);
        user.setPassword(this.passwordEncoder.encode(newPassword));
        this.userRepository.save(user);
        this.mailService.sendPasswordRecovery(user, newPassword, locale);
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void changeFullName(Authentication authentication, FullNameChangeForm fullNameChangeForm) {
        Optional<User> optionalUser = this.userRepository.findById(((UserDetailsImpl) authentication.getPrincipal()).getId());
        optionalUser.ifPresent(user -> {
            user.setFirstName(fullNameChangeForm.getFirstName());
            user.setLastName(fullNameChangeForm.getLastName());
            this.userRepository.save(user);
        });
    }

}
