package mindhub.VoyagerRestaurante.services.implementation;

import mindhub.VoyagerRestaurante.dtos.AllProductsDTO;
import mindhub.VoyagerRestaurante.dtos.ClientDTO;
import mindhub.VoyagerRestaurante.dtos.ProductDTO;
import mindhub.VoyagerRestaurante.models.Product;
import mindhub.VoyagerRestaurante.repositories.ProductRepository;
import mindhub.VoyagerRestaurante.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public  List<ProductDTO> getAllProductsDTO(){return getAllProducts().stream()
            .map(ProductDTO::new)
            .collect(Collectors.toList());}

    @Override
    public  List<AllProductsDTO> getALLAllProductsDTO(){return getAllProducts().stream()
            .map(AllProductsDTO::new)
            .collect(Collectors.toList());}

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProductsByIds(List<Long> ids) {
        return productRepository.findAllById(ids); // Obtener los productos usando los IDs
    }
}
