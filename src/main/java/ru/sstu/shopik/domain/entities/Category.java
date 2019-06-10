package ru.sstu.shopik.domain.entities;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private int categoryId;

    @Column(name = "categoryRu", length = 50, nullable = false)
    private String ruCategory;

    @Column(name = "categoryEn", length = 50, nullable = false)
    private String enCategory;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "motherCategory")
    private List<Category> subCategories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motherId", nullable = false)
    private Category motherCategory;

    public Category(){}

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getRuCategory() {
        return ruCategory;
    }

    public void setRuCategory(String ruCategory) {
        this.ruCategory = ruCategory;
    }

    public String getEnCategory() {
        return enCategory;
    }

    public void setEnCategory(String enCategory) {
        this.enCategory = enCategory;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public Category getMotherCategory() {
        return motherCategory;
    }

    public void setMotherCategory(Category motherCategory) {
        this.motherCategory = motherCategory;
    }

}