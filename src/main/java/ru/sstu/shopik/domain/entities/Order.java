package ru.sstu.shopik.domain.entities;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Long id;



/*    @ManyToOne
    @JoinColumn(name = "buyerId", nullable = false)
    private User buyer;*/




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statusId", nullable = false)
    private OrderStatus status;


    @Column(name = "date", nullable = false)
    private Date date;

}
