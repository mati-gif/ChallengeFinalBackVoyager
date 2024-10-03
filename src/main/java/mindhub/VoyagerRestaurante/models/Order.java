package mindhub.VoyagerRestaurante.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")  // Cambié el nombre de la tabla a "orders"
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;
    private double totalAmount;

    @Enumerated(EnumType.STRING)  // Especificar que el enum se mapea como string
    private OrderType orderType;

    @Enumerated(EnumType.STRING)  // Especificar que el enum se mapea como string
    private OrderStatusType orderStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adress_id")
    private Adress adress;

    public Order() {
    }

    public Order(LocalDateTime orderDate, double totalAmount, OrderType orderType, OrderStatusType orderStatusType) {
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderType = orderType;
        this.orderStatus = orderStatusType;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public OrderStatusType getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusType orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        if (this.orderType == OrderType.DELIVERY) {
            this.adress = adress;
        } else {
            throw new UnsupportedOperationException("Address is only for delivery orders.");
        }
    }
}