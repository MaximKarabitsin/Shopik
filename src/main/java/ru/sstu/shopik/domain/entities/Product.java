package ru.sstu.shopik.domain.entities;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long id;

    @Column(name = "productName", nullable = false, length = 50)
    private String productName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sellerId", nullable = false)
    private User seller;

    @Column(name = "cost")
    private int cost;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "discount")
    private int discount;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;


    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getCostWithDiscount() {
        return (int)((long)(this.cost) * (100 - this.discount)/100);
    }
}
