package ru.sstu.shopik.domain.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "newsId")
    private int newsId;

    @Column(name = "newsTitle", nullable = false, length = 50)
    private String newsTitle;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "date")
    private Date date;

    public News() {
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
