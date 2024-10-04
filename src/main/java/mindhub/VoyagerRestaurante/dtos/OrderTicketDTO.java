package mindhub.VoyagerRestaurante.dtos;
//RESPUESTA A LA ORDEN
import java.util.List;

public class OrderTicketDTO {
    private List<ProductDTO> products;
    private String orderType;
    private String address;  // Solo se incluye si la orden es DELIVERY
    private double totalAmount;

    public OrderTicketDTO(List<ProductDTO> products, String orderType, String address, double totalAmount) {
        this.products = products;
        this.orderType = orderType;
        this.address = address;
        this.totalAmount = totalAmount;
    }

    public OrderTicketDTO(List<ProductDTO> products, String orderType, double totalAmount) {
        this(products, orderType, null, totalAmount);
    }

    // Getters y setters
    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
