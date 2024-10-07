package mindhub.VoyagerRestaurante.controllers;

import mindhub.VoyagerRestaurante.dtos.*;
import mindhub.VoyagerRestaurante.models.*;
import mindhub.VoyagerRestaurante.serviceSecurity.JwtUtilService;
import mindhub.VoyagerRestaurante.services.ClientService;
import mindhub.VoyagerRestaurante.services.OrderService;
import mindhub.VoyagerRestaurante.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Product> createProduct(@RequestBody ProductCreateDTO productCreateDTO) {

        Product newProduct = new Product(productCreateDTO.nameProduct(),productCreateDTO.priceProduct(),productCreateDTO.category(),productCreateDTO.details(),productCreateDTO.img());
        productService.saveProduct(newProduct);
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

    //Modificar la propiedad nameProduct(nombre del producto)
    @PatchMapping("/nameProduct")
    public ResponseEntity<?> updateNameProduct(@RequestParam String nameProduct,@RequestParam Long id) {

        Optional<Product> product = productService.getProductById(id);

        if(product == null){
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        product.get().setNameProduct(nameProduct);

        AllProductsDTO updatedProduct = productService.saveUpdatedProduct(product.get());
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PatchMapping("/priceProduct")
    public ResponseEntity<?> updatePriceProduct(@RequestParam double priceProduct,@RequestParam Long id) {

        Optional<Product> product = productService.getProductById(id);

        if(product == null){
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        product.get().setPriceProduct(priceProduct);

        AllProductsDTO updatedProduct = productService.saveUpdatedProduct(product.get());
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PatchMapping("/category")
    public ResponseEntity<?> updateCategory(@RequestParam Category category,@RequestParam Long id) {

        Optional<Product> product = productService.getProductById(id);

        if(product == null){
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        product.get().setCategory(category);

        AllProductsDTO updatedProduct = productService.saveUpdatedProduct(product.get());
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PatchMapping("/details")
    public ResponseEntity<?> updateDetails(@RequestParam String details,@RequestParam Long id) {

        Optional<Product> product = productService.getProductById(id);

        if(product == null){
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        product.get().setDetails(details);

        AllProductsDTO updatedProduct = productService.saveUpdatedProduct(product.get());
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PatchMapping("/img")
    public ResponseEntity<?> updateImg(@RequestBody ModifyImgProductDTO modifyImgProductDTO) {

        Optional<Product> product = productService.getProductById(modifyImgProductDTO.id());

        if(product == null){
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        product.get().setImg(modifyImgProductDTO.img());

        AllProductsDTO updatedProduct = productService.saveUpdatedProduct(product.get());
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }



    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ModifyAllProductDTO allProductsDTO) {
        Product product = productService.getProductByIdd(allProductsDTO.id());
        if(product == null){
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }


        if(allProductsDTO.nameProduct() != null){

            product.setNameProduct(allProductsDTO.nameProduct());
        }
        if(allProductsDTO.priceProduct() != null){
            product.setPriceProduct(allProductsDTO.priceProduct());

        }
        if (allProductsDTO.category() != null) {
            product.setCategory(allProductsDTO.category());

        }
        if(allProductsDTO.details() != null){
            product.setDetails(allProductsDTO.details());

        }
        if(allProductsDTO.img() != null){
            product.setImg(allProductsDTO.img());

        }

        AllProductsDTO updatedProduct = productService.saveUpdatedProduct(product);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }


    // Eliminar un producto por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
