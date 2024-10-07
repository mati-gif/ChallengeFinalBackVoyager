package mindhub.VoyagerRestaurante.services;

import mindhub.VoyagerRestaurante.dtos.AllProductsDTO;
import mindhub.VoyagerRestaurante.dtos.ProductDTO;
import mindhub.VoyagerRestaurante.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product saveProduct(Product product);

    List<ProductDTO> getAllProductsDTO();

    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    void deleteProduct(Long id);

    List<Product> getProductsByIds(List<Long> ids);

    List<AllProductsDTO> getALLAllProductsDTO();

    AllProductsDTO saveUpdatedProduct(Product product);

    Product getProductByIdd(Long id);
}

