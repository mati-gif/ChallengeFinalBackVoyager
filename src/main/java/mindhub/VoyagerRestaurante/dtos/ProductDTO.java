package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.Category;
import mindhub.VoyagerRestaurante.models.Product;

public class ProductDTO {
    private String nameProduct;
    private double priceProduct;
    private Category category;
    private int quantities; // Cantidad del producto

    // Constructor para usar solo Product
    public ProductDTO(Product product) {
        this.nameProduct = product.getNameProduct();
        this.priceProduct = product.getPriceProduct();
        this.category = product.getCategory();
    }

    // Constructor que incluye Product y la cantidad
    public ProductDTO(Product product, int quantities) {
        this.nameProduct = product.getNameProduct();
        this.priceProduct = product.getPriceProduct();
        this.category = product.getCategory();
        this.quantities = quantities;
    }

    // Getters y setters
    public String getNameProduct() {
        return nameProduct;
    }

    public double getPriceProduct() {
        return priceProduct;
    }

    public Category getCategory() {
        return category;
    }

    public int getQuantities() {
        return quantities;
    }

    public void setQuantities(int quantities) {
        this.quantities = quantities;
    }
}
