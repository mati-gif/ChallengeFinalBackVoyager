package mindhub.VoyagerRestaurante.dtos;

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

}
