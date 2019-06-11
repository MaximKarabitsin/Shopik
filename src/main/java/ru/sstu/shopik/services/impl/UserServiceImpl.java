package ru.sstu.shopik.services.impl;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sstu.shopik.dao.RoleRepository;
import ru.sstu.shopik.dao.UserRepository;
import ru.sstu.shopik.domain.UserDetailsImpl;
import ru.sstu.shopik.domain.entities.Role;
import ru.sstu.shopik.domain.entities.User;
import ru.sstu.shopik.exceptions.InvalidCurrentPassword;
import ru.sstu.shopik.exceptions.UserDoesNotExist;
import ru.sstu.shopik.forms.FullNameChangeForm;
import ru.sstu.shopik.forms.PasswordChangeForm;
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
    public void confirmUserEmail(String token) throws UserDoesNotExist {
        Optional<User> optionalUser = this.userRepository.findByToken(token);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            this.userRepository.save(user);
        } else {
            throw new UserDoesNotExist();
        }
    }

    @Override
    public void recoverUserPassword(PasswordRecoveryForm passwordRecoveryForm, Locale locale) throws UserDoesNotExist {
        Optional<User> optionalUser = this.userRepository.findByEmail(passwordRecoveryForm.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String newPassword = RandomStringUtil.generateString(10);
            user.setPassword(this.passwordEncoder.encode(newPassword));
            this.userRepository.save(user);
            this.mailService.sendPasswordRecovery(user, newPassword, locale);
        } else {
            throw new UserDoesNotExist();
        }
    }

    @Override
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void changeUserFullName(Authentication authentication, FullNameChangeForm fullNameChangeForm) throws UserDoesNotExist {
        Optional<User> optionalUser = this.getUserFromAuthentication(authentication);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(fullNameChangeForm.getFirstName());
            user.setLastName(fullNameChangeForm.getLastName());
            this.userRepository.save(user);
        } else {
            throw new UserDoesNotExist();
        }
    }

    @Override
    public Optional<User> getUserFromAuthentication(Authentication authentication) {
        return this.getUserById(((UserDetailsImpl) authentication.getPrincipal()).getId());
    }

    @Override
    public void changeUserPassword(Authentication authentication, PasswordChangeForm passwordChangeForm) throws UserDoesNotExist, InvalidCurrentPassword {
        Optional<User> optionalUser = this.getUserFromAuthentication(authentication);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!this.passwordEncoder.matches(passwordChangeForm.getCurrentPassword(), user.getPassword())) {
                throw new InvalidCurrentPassword();
            }
            user.setPassword(this.passwordEncoder.encode(passwordChangeForm.getNewPassword()));
            this.userRepository.save(user);
        } else {
            throw new UserDoesNotExist();
        }
    }

    @Override
    public Page<User> getPageUser(int page) {


        return this.userRepository.findAll(PageRequest.of(page,5));
    }
}
