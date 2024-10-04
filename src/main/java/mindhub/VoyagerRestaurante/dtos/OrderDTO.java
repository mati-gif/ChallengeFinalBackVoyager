package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.Order;
import mindhub.VoyagerRestaurante.models.OrderProduct;
import mindhub.VoyagerRestaurante.models.OrderStatusType;
import mindhub.VoyagerRestaurante.models.OrderType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO {

    private Long id;
    private LocalDateTime orderDate;
    private double totalAmount;
    private OrderType orderType;
    private OrderStatusType orderStatus;
    private List<ProductDTO> products;


    // Constructor que acepta un objeto Order
    public OrderDTO(Order order) {
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.totalAmount = order.getTotalAmount();
        this.orderType = order.getOrderType();
        this.orderStatus = order.getOrderStatus();
        // Mapeamos los productos asociados a la orden
        this.products = order.getOrderProducts().stream().map(orderProduct -> new ProductDTO(orderProduct.getProduct(), orderProduct.getQuantities()))
                .collect(Collectors.toList());
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public OrderType getOrderType() {
        return orderType;
    }


    public OrderStatusType getOrderStatus() {
        return orderStatus;
    }

    public List<ProductDTO> getProducts() {
        return products;

    }
}
