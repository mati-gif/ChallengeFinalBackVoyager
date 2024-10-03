package mindhub.VoyagerRestaurante.controllers;

import mindhub.VoyagerRestaurante.dtos.OrderTicketDTO;
import mindhub.VoyagerRestaurante.dtos.ProductDTO;
import mindhub.VoyagerRestaurante.dtos.PurchaseRequestDTO;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.Order;
import mindhub.VoyagerRestaurante.models.Product;
import mindhub.VoyagerRestaurante.serviceSecurity.JwtUtilService;
import mindhub.VoyagerRestaurante.services.ClientService;
import mindhub.VoyagerRestaurante.services.OrderService;
import mindhub.VoyagerRestaurante.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    @Autowired
    private JwtUtilService jwtUtilService;

    // Método para retornar el ticket al cliente autenticado basándose en los productos comprados
    @PostMapping("/ticket")
    public ResponseEntity<OrderTicketDTO> getOrderTicket(@RequestHeader("Authorization") String token, @RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        // Extraer el token sin el prefijo "Bearer "
        String jwtToken = token.substring(7);

        // Obtener el email del cliente desde el token
        String email = jwtUtilService.extractUsername(jwtToken);

        // Buscar el cliente autenticado por su email
        Client client = clientService.findByEmail(email);
//                .orElseThrow(() -> new RuntimeException("Client not found"));

        // Validar si los productos existen en la base de datos
        List<Product> products = productService.getProductsByIds(purchaseRequestDTO.getProductIds());
        if (products.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Usamos un array para permitir la modificación de `totalAmount` dentro de la lambda
        final double[] totalAmount = {0};

        // Calcular el total de la compra y crear la lista de ProductDTO
        List<ProductDTO> productDTOList = products.stream()
                .map(product -> {
                    int quantity = purchaseRequestDTO.getQuantities().get(purchaseRequestDTO.getProductIds().indexOf(product.getId()));
                    totalAmount[0] += product.getPriceProduct() * quantity;
                    return new ProductDTO(product.getNameProduct(), product.getPriceProduct());
                })
                .collect(Collectors.toList());

        // Crear el DTO del ticket de la compra
        OrderTicketDTO orderTicketDTO = new OrderTicketDTO(productDTOList, totalAmount[0]);

        // Retornar el ticket con los productos y el total
        return ResponseEntity.ok(orderTicketDTO);
    }


    // Obtener todas las órdenes
    @GetMapping("/")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Guardar una nueva orden (puedes integrar esto dentro del flujo de compra del cliente)
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order newOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(newOrder);
    }

    // Eliminar una orden por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
