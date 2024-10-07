package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.OrderType;

import java.util.List;

public class PurchaseRequestDTO {
    private List<Long> productIds;  // IDs de los productos que el cliente desea comprar
    private List<Integer> quantities;  // Cantidades de cada producto
    private String orderType;  // Tipo de orden (DELIVERY, TAKEOUT, DINNER_IN)
    private Long addressId;  // ID de la dirección (solo si es DELIVERY)

    public PurchaseRequestDTO() {}

    public PurchaseRequestDTO(List<Long> productIds, List<Integer> quantities, String orderType, Long addressId) {
        this.productIds = productIds;
        this.quantities = quantities;
        this.orderType = orderType;
        this.addressId = addressId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}
