package mindhub.VoyagerRestaurante.controllers;

import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.Product;
import mindhub.VoyagerRestaurante.models.ReviewClientProduct;
import mindhub.VoyagerRestaurante.services.ClientService;
import mindhub.VoyagerRestaurante.services.ProductService;
import mindhub.VoyagerRestaurante.services.ReviewClientProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewClientProductController {

    @Autowired
    private ReviewClientProductService reviewService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    // Crear una nueva reseña
    @PostMapping("/create")
    public ResponseEntity<ReviewClientProduct> createReview(@RequestParam Long clientId,
                                                            @RequestParam Long productId,
                                                            @RequestBody ReviewClientProduct reviewDetails) {
        Client client = clientService.getClientById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
        Product product = productService.getProductById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        reviewDetails.setClient(client);
        reviewDetails.setProduct(product);

        ReviewClientProduct newReview = reviewService.saveReview(reviewDetails);
        return ResponseEntity.ok(newReview);
    }

    // Obtener todas las reseñas de un producto
    @GetMapping("/product/{productId}")
    public List<ReviewClientProduct> getReviewsByProduct(@PathVariable Long productId) {
        return reviewService.getReviewsByProduct(productId);
    }

    // Obtener todas las reseñas de un cliente
    @GetMapping("/client/{clientId}")
    public List<ReviewClientProduct> getReviewsByClient(@PathVariable Long clientId) {
        return reviewService.getReviewsByClient(clientId);
    }

    // Eliminar una reseña
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}

