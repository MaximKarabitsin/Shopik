package ru.sstu.shopik.services;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sstu.shopik.dao.RoleRepository;
import ru.sstu.shopik.dao.UserRepository;
import ru.sstu.shopik.domain.entities.Role;
import ru.sstu.shopik.domain.entities.User;
import ru.sstu.shopik.forms.UserRegistrationForm;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean isUserWithLoginExist(String login) {
        return userRepository.countByLogin(login) != 0 ? true : false;
    }

    @Override
    public boolean isUserWithEmailExist(String email) {
        return userRepository.countByEmail(email) != 0 ? true : false;
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
        user.setEnabled(true);
        userRepository.save(user);
    }
}
