package mindhub.VoyagerRestaurante.controllers;

import mindhub.VoyagerRestaurante.dtos.AllProductsDTO;
import mindhub.VoyagerRestaurante.dtos.ProductDTO;
import mindhub.VoyagerRestaurante.dtos.PurchaseRequestDTO;
import mindhub.VoyagerRestaurante.models.*;
import mindhub.VoyagerRestaurante.serviceSecurity.JwtUtilService;
import mindhub.VoyagerRestaurante.services.ClientService;
import mindhub.VoyagerRestaurante.services.OrderService;
import mindhub.VoyagerRestaurante.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtilService jwtUtilService;

//    @PostMapping("/purchase")
//    public ResponseEntity<?> purchaseProducts(@RequestBody PurchaseRequestDTO purchaseRequestDTO, Authentication authentication) {
//        Client client = clientService.findByEmail(authentication.getName());
//
//        // Validar si los productos existen
//        List<Product> products = productService.getProductsByIds(purchaseRequestDTO.getProductIds());
//        if (products.isEmpty()) {
//            return ResponseEntity.badRequest().body("Some products not found");
//        }
//
//        // Crear una nueva orden para el cliente
//        double totalAmount = 0;
//        for (int i = 0; i < products.size(); i++) {
//            Product product = products.get(i);
//            int quantity = purchaseRequestDTO.getQuantities().get(i);
//
////             Crear y guardar la orden
//            Order order = new Order(LocalDateTime.now(), product.getPriceProduct() * quantity, OrderType.DELIVERY);
//            order.setProduct(product);
//            orderService.saveOrder(order);
//
//            // Acumular el total
//            totalAmount += product.getPriceProduct() * quantity;
//        }
//
//        // Retornar el total de la compra
//        return ResponseEntity.ok("Purchase successful. Total amount: " + totalAmount);
//    }

    // Crear un nuevo producto
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.saveProduct(product);
        return ResponseEntity.ok(newProduct);
    }

    // Obtener todos los productos
    @GetMapping("/")
    public List<AllProductsDTO> getAllProducts() {
        return productService.getALLAllProductsDTO();
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar un producto por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
