package mindhub.VoyagerRestaurante.controllers;

import mindhub.VoyagerRestaurante.dtos.OrderTicketDTO;
import mindhub.VoyagerRestaurante.dtos.ProductDTO;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.Order;
import mindhub.VoyagerRestaurante.serviceSecurity.JwtUtilService;
import mindhub.VoyagerRestaurante.services.ClientService;
import mindhub.VoyagerRestaurante.services.OrderService;
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
    private JwtUtilService jwtUtilService;

    // Método para retornar el ticket al cliente autenticado
    @GetMapping("/ticket")
    public ResponseEntity<OrderTicketDTO> getOrderTicket(@RequestHeader("Authorization") String token) {
        // Extraer el token sin el prefijo "Bearer "
        String jwtToken = token.substring(7);

        // Obtener el email del cliente desde el token
        String email = jwtUtilService.extractUsername(jwtToken);

        // Buscar el cliente autenticado por su email
        Client client = clientService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        // Obtener todas las órdenes del cliente
        List<Order> orders = orderService.getOrdersByClient(client);

        // Calcular el total de la compra
        double totalAmount = orders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();

        // Convertir los productos de las órdenes a ProductDTO
        List<ProductDTO> productList = orders.stream()
                .map(Order::getProduct)
                .map(product -> new ProductDTO(product.getNameProduct(), product.getPriceProduct()))
                .collect(Collectors.toList());

        // Crear el DTO de la respuesta
        OrderTicketDTO ticketDTO = new OrderTicketDTO(productList, totalAmount);

        return ResponseEntity.ok(ticketDTO);
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
