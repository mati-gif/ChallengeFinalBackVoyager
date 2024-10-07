package mindhub.VoyagerRestaurante.controllers;


import mindhub.VoyagerRestaurante.dtos.*;
import mindhub.VoyagerRestaurante.models.*;
import mindhub.VoyagerRestaurante.serviceSecurity.JwtUtilService;
import mindhub.VoyagerRestaurante.services.ClientService;
import mindhub.VoyagerRestaurante.services.OrderService;
import mindhub.VoyagerRestaurante.services.PaymentService;
import mindhub.VoyagerRestaurante.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @Autowired
    private PaymentService paymentService;  // Servicio para comunicarse con homebanking

    // Método para crear una orden
    @PostMapping("/create")
    public ResponseEntity<CreateOrderDTO> createOrder(@RequestHeader("Authorization") String token, @RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        // Extraer el token sin el prefijo "Bearer "
        String jwtToken = token.substring(7);

        // Obtener el email del cliente desde el token
        String email = jwtUtilService.extractUsername(jwtToken);

        // Buscar el cliente autenticado por su email
        Client client = clientService.findByEmail(email);

        if (client == null) {
            return ResponseEntity.badRequest().body(null); // Error si el cliente no existe
        }

        // Validar si los productos existen en la base de datos
        List<Product> products = productService.getProductsByIds(purchaseRequestDTO.getProductIds());
        if (products.isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Error si no se encuentran productos
        }

        // Verificar el tipo de orden (si es DELIVERY, se necesita la dirección)
        if (purchaseRequestDTO.getOrderType() == OrderType.DELIVERY) {
            if (purchaseRequestDTO.getAddressId() == null) {
                return ResponseEntity.badRequest().body(null); // Dirección requerida
            }
            Adress deliveryAddress = clientService.getAddressById(purchaseRequestDTO.getAddressId());
            if (deliveryAddress == null) {
                return ResponseEntity.badRequest().body(null); // Dirección no encontrada
            }
        }

        // Crear la instancia de Order y asociar los productos
        Order newOrder = new Order();
        newOrder.setClient(client);
        newOrder.setOrderType(purchaseRequestDTO.getOrderType());
        newOrder.setOrderStatus(OrderStatusType.IN_PROCESS);
        newOrder.setOrderDate(LocalDateTime.now());

        final double[] totalAmount = {0};

        // Asociar los productos con la orden y calcular el total
        products.forEach(product -> {
            int quantity = purchaseRequestDTO.getQuantities().get(purchaseRequestDTO.getProductIds().indexOf(product.getId()));
            totalAmount[0] += product.getPriceProduct() * quantity;

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(newOrder);
            orderProduct.setProduct(product);
            orderProduct.setQuantities(quantity);
            orderProduct.setTotalAmount(product.getPriceProduct() * quantity);

            newOrder.getOrderProducts().add(orderProduct);
        });

        // Asignar el total a la orden
        newOrder.setTotalAmount(totalAmount[0]);

        // Guardar la orden en la base de datos
        orderService.saveOrder(newOrder);

        // Crear una instancia de CreateOrderDTO y asignar valores
        CreateOrderDTO createOrderDTO = new CreateOrderDTO(); // Instanciar el DTO
        createOrderDTO.setOrderId(newOrder.getId()); // Usar métodos de instancia
        createOrderDTO.setAmount(totalAmount[0]);

        // Retornar el DTO necesario para el pago
        return ResponseEntity.ok(createOrderDTO);
    }


    @PostMapping("/initiate-payment")
    public ResponseEntity<PaymentResponseDTO> initiatePayment(
            @RequestHeader("Authorization") String token,  // Recibe el token desde el header
            @RequestBody PaymentRequestDTO paymentRequestDTO) {

        // Extraer el token sin el prefijo "Bearer "
        String jwtToken = token.substring(7);

        // Obtener el email del cliente desde el token
        String email = jwtUtilService.extractUsername(jwtToken);

        // Buscar el cliente autenticado por su email
        Client client = clientService.findByEmail(email);

        if (client == null) {
            return ResponseEntity.badRequest().body(null); // Error si el cliente no existe
        }

        // Buscar la orden por su ID
        Order order = orderService.getOrderById(paymentRequestDTO.getOrderId());
        if (order == null) {
            return ResponseEntity.badRequest().body(null); // Error si la orden no existe
        }

        // Validar que la orden pertenezca al cliente autenticado
        if (!order.getClient().getId().equals(client.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // Error si la orden no pertenece al cliente autenticado
        }

        // Iniciar el pago a través del sistema de homebanking usando PaymentService
        PaymentResponseDTO paymentResponse = paymentService.initiatePayment(order, paymentRequestDTO.getCardDetailsDTO());

        if (paymentResponse.isSuccess()) {
            return ResponseEntity.ok(paymentResponse);
        } else {
            return ResponseEntity.badRequest().body(paymentResponse);
        }
    }


    // Obtener todas las órdenes
    @GetMapping("/")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Eliminar una orden por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener las órdenes del cliente autenticado en formato DTO
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderDTO>> getClientOrders(@RequestHeader("Authorization") String token) {
        // Extraer el token sin el prefijo "Bearer "
        String jwtToken = token.substring(7);

        // Obtener el email del cliente desde el token
        String email = jwtUtilService.extractUsername(jwtToken);

        // Buscar el cliente autenticado por su email
        Client client = clientService.findByEmail(email);

        // Obtener las órdenes del cliente autenticado
        List<Order> clientOrders = orderService.getOrdersByClient(client);

        // Convertir las órdenes a DTOs utilizando el constructor de OrderDTO
        List<OrderDTO> orderDTOs = clientOrders.stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());

        // Retornar la lista de órdenes en formato DTO
        return ResponseEntity.ok(orderDTOs);
    }
}