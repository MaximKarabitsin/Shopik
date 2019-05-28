package ru.sstu.shopik;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sstu.shopik.dao.RoleRepository;
import ru.sstu.shopik.dao.UserRepository;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;



@RequestMapping("/")
    public String index() {

/*        User user = new User();
        Set<Role> set = new HashSet<Role>();
        set.add(roleRepository.findByRole("USER"));
        user.setRoles(set);
user.setLogin("user");
user.setPassword(new BCryptPasswordEncoder().encode("user"));
user.setBalance(123);
user.setDate(new Date());
user.setEmail("User@mail.ru");
        user.setEnabled(true);

userRepository.save(user);*/
        return "index";
    }
}
