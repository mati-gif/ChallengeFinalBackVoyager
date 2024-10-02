package mindhub.VoyagerRestaurante.dtos;

import java.util.List;

public class PurchaseRequestDTO {
    private List<Long> productIds;  // Lista de productos que el cliente desea comprar
    private List<Integer> quantities;  // Cantidad de cada producto

    public PurchaseRequestDTO() {}

    public PurchaseRequestDTO(List<Long> productIds, List<Integer> quantities) {
        this.productIds = productIds;
        this.quantities = quantities;
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
}

