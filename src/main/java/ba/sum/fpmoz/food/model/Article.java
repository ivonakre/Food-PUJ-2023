package ba.sum.fpmoz.food.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    @NotBlank(message = "Molimo unesite naziv proizvoda.")
    String name;

    @Column(nullable = false)
    @NotBlank(message = "Molimo unesite opis proizvoda.")
    String description;

    @Column(nullable = false)
    @NotNull(message = "Molimo unesite cijenu proizvoda.")
    Float price;

    @Column(nullable = false)
    String image;

    @Column(nullable = false)
    Integer rating_number;

    @ManyToMany(mappedBy = "articles")
    List<Invoice> invoices;

    @OneToMany(mappedBy = "article")
    List<Rating> ratings;

    public Article() {
    }

    public Article(Long id, String name, String description, Float price, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.rating_number = rating_number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getRating_number() {
        return rating_number;
    }

    public void setRating_number(Integer rating_number) {
        this.rating_number = rating_number;
    }

    public List<Rating> getRantings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
}