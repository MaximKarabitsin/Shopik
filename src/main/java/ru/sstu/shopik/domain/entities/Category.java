package ru.sstu.shopik.domain.entities;


import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private int categoryId;

    @Column(name = "category")
    private String category;

    @Column(name = "motherId")
    private int motherId;

    public Category(){}

    public int getCategory() {
        return categoryId;
    }

    public void setCategory(int category) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return category;
    }

    public void setCategoryName(String categoryName) {
        this.category = categoryName;
    }

    public int getMotherId() {
        return motherId;
    }

    public void setMotherId(int motherId) {
        this.motherId = motherId;
    }
}
