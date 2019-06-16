package ru.sstu.shopik.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.sstu.shopik.domain.entities.Product;
import ru.sstu.shopik.domain.entities.User;
import ru.sstu.shopik.domain.entities.WishList;

import java.util.List;


public interface WishListRepository extends JpaRepository<WishList, Long> {
    Page<WishList> findAllByUser(User user, Pageable pageable);

    int countByProductAndUser(Product product, User user);

    int countByProduct(Product product);

    List<WishList> findAllByProduct(Product product);

    @Transactional
    void deleteAllByProduct(Product product);

}
