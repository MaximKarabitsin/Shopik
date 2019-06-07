package ru.sstu.shopik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sstu.shopik.domain.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    Optional<User> findByToken(String token);
    int countByLogin(String login);
    int countByEmail(String email);
    int countByEnabledAndEmail(Boolean enabled, String email);
}
