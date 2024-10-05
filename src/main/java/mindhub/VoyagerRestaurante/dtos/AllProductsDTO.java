package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.Category;
import mindhub.VoyagerRestaurante.models.Product;

public class AllProductsDTO {

    private long id;
    private String nameProduct;
    private double priceProduct;
    private Category category;
    private String details;
    private String img;

    public AllProductsDTO(Product product) {
        this.id = product.getId();
        this.nameProduct = product.getNameProduct();
        this.priceProduct = product.getPriceProduct();
        this.category = product.getCategory();
        this.details = product.getDetails();
        this.img = product.getImg();
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(double priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImg() {
        return img;
    }

    public long getId() {
        return id;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

