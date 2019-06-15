package ru.sstu.shopik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sstu.shopik.domain.entities.News;

public interface NewsRepository extends JpaRepository<News, Integer> {
}
