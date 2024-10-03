package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.Order;
import mindhub.VoyagerRestaurante.models.OrderStatusType;
import mindhub.VoyagerRestaurante.models.OrderType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderDTO {

    private Long id;
    private LocalDateTime orderDate;
    private double totalAmount;
    private OrderType orderType;
    private OrderStatusType orderStatusType;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.totalAmount = order.getTotalAmount();
        this.orderType = order.getOrderType();
        this.orderStatusType = order.getOrderStatus();
    }

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

    public OrderStatusType getOrderStatusType() {
        return orderStatusType;
    }
}
