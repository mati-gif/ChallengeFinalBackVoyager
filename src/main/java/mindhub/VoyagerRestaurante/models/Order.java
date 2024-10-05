package mindhub.VoyagerRestaurante.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "adress_id")
    private Adress adress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference  // Para manejar la relación bidireccional con OrderProduct
    private List<OrderProduct> orderProducts = new ArrayList<>();  // Relación con OrderProduct

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

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    // Método para añadir un producto a la orden
    public void addOrderProduct(OrderProduct orderProduct) {
        orderProduct.setOrder(this);  // Asociar el producto a esta orden
        this.orderProducts.add(orderProduct);  // Añadirlo a la lista
    }
}
