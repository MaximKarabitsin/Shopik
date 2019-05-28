package ru.sstu.shopik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sstu.shopik.domain.entities.Role;

public interface RoleRepository  extends JpaRepository<Role, Integer> {
    Role findByRole(String login);

}
