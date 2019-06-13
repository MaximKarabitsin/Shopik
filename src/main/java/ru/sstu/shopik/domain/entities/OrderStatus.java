package ru.sstu.shopik.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orderStatus")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderStatusId")
    private Long id;

}
