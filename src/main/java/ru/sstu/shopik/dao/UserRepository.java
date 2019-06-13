package ru.sstu.shopik.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sstu.shopik.domain.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
    int countByLogin(String login);
    int countByEmail(String email);
    int countByToken(String token);
    int countByEnabledAndEmail(Boolean enabled, String email);
    Page<User> findAll(Pageable pageable);
    int countById(Long id);
}
