package mindhub.VoyagerRestaurante.dtos;

public class CreateOrderDTO {
    private Long orderId;
    private double amount;

    // Getters y Setters

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
