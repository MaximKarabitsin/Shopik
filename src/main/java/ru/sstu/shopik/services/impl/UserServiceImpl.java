package ru.sstu.shopik.services.impl;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sstu.shopik.dao.RoleRepository;
import ru.sstu.shopik.dao.UserRepository;
import ru.sstu.shopik.domain.entities.Role;
import ru.sstu.shopik.domain.entities.User;
import ru.sstu.shopik.forms.PasswordRecoveryForm;
import ru.sstu.shopik.forms.UserRegistrationForm;
import ru.sstu.shopik.services.MailService;
import ru.sstu.shopik.services.UserService;

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
        return userRepository.countByLogin(login) != 0 ? true : false;
    }

    @Override
    public boolean isUserWithEmailExist(String email) {
        return userRepository.countByEmail(email) != 0 ? true : false;
    }

    @Override
    public boolean isUserWithEmailExistAndEnabled(String email) {
        return userRepository.countByEnabledAndEmail(true, email) != 0 ? true : false;
    }

    @Override
    public void createUserFromRegistrationForm(UserRegistrationForm userForm) {
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRole("USER"));
        if (userForm.getRole().equals("seller")) {
            roles.add(roleRepository.findByRole("SELLER"));
        }
        user.setRoles(roles);
        user.setBalance(0);
        user.setDate(new Date());
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setEnabled(false);
        userRepository.save(user);
        mailService.sendConfirmEmail(user);
    }

    @Override
    public boolean confirmEmail(String token) {
        Optional<User> optionalUser = userRepository.findByToken(token);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
                user.setEnabled(true);
                userRepository.save(user);
                return true;
        }
        return false;
    }

    @Override
    public void recoverPassword(PasswordRecoveryForm passwordRecoveryForm) {

    }
}
