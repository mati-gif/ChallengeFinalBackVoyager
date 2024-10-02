package mindhub.VoyagerRestaurante.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameProduct;
    private Double priceProduct;

    @Enumerated(EnumType.STRING)
    private Category category; // Supongo que tienes un enum Category

    private String details;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<ReviewClientProduct> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<>();

    public Product() {
    }

    public Product(String nameProduct, Double priceProduct, Category category, String details) {
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.category = category;
        this.details = details;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(Double priceProduct) {
        this.priceProduct = priceProduct;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<ReviewClientProduct> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewClientProduct> reviews) {
        this.reviews = reviews;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addReview(ReviewClientProduct review) {
        this.reviews.add(review);
        review.setProduct(this);
    }

    public void addOrder(Order order){
        this.orders.add(order);
        order.setProduct(this);
    }
}
