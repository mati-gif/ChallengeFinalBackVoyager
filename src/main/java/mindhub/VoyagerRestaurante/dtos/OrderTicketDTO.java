package mindhub.VoyagerRestaurante.dtos;

import java.util.List;

public class OrderTicketDTO {
    private List<ProductDTO> products;
    private double totalAmount;

    public OrderTicketDTO(List<ProductDTO> products, double totalAmount) {
        this.products = products;
        this.totalAmount = totalAmount;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}

