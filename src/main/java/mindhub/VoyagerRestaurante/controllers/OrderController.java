package mindhub.VoyagerRestaurante.controllers;

import mindhub.VoyagerRestaurante.dtos.OrderDTO;
import mindhub.VoyagerRestaurante.dtos.OrderTicketDTO;
import mindhub.VoyagerRestaurante.dtos.ProductDTO;
import mindhub.VoyagerRestaurante.dtos.PurchaseRequestDTO;
import mindhub.VoyagerRestaurante.models.*;
import mindhub.VoyagerRestaurante.serviceSecurity.JwtUtilService;
import mindhub.VoyagerRestaurante.services.ClientService;
import mindhub.VoyagerRestaurante.services.OrderService;
import mindhub.VoyagerRestaurante.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Método para crear una orden y retornar el ticket al cliente autenticado
    @PostMapping("/create")
    public ResponseEntity<OrderTicketDTO> createOrder(@RequestHeader("Authorization") String token, @RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        // Extraer el token sin el prefijo "Bearer "
        String jwtToken = token.substring(7);

        // Obtener el email del cliente desde el token
        String email = jwtUtilService.extractUsername(jwtToken);

        // Buscar el cliente autenticado por su email
        Client client = clientService.findByEmail(email);

        // Validar si los productos existen en la base de datos
        List<Product> products = productService.getProductsByIds(purchaseRequestDTO.getProductIds());
        if (products.isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Error si no se encuentran productos
        }

        // Verificar el tipo de orden (si es DELIVERY, se necesita la dirección)
        String address = null;
        if (purchaseRequestDTO.getOrderType() == OrderType.DELIVERY) {
            if (purchaseRequestDTO.getAddressId() == null) {
                return ResponseEntity.badRequest().body(null); // Dirección requerida
            }
            address = clientService.getAddressById(purchaseRequestDTO.getAddressId()).getNameStreet();
        }

        // Crear la instancia de Order y asociar los productos
        Order newOrder = new Order();
        newOrder.setClient(client); // Asociar al cliente autenticado
        newOrder.setOrderType(purchaseRequestDTO.getOrderType()); // Establecer el tipo de orden
        newOrder.setOrderStatus(OrderStatusType.IN_PROCESS); // Establecer el estado de la orden a IN_PROCESS
        newOrder.setOrderDate(LocalDateTime.now()); // Establecer la fecha actual de la orden

        // Calcular el total de la compra
        final double[] totalAmount = {0};

        // Crear la lista de productos y calcular el totalAmount
        List<ProductDTO> productDTOList = products.stream()
                .map(product -> {
                    int quantity = purchaseRequestDTO.getQuantities().get(purchaseRequestDTO.getProductIds().indexOf(product.getId()));
                    totalAmount[0] += product.getPriceProduct() * quantity;

                    // Crear y asociar OrderProduct (entidad intermedia) con la orden
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setOrder(newOrder); // Asociar a la nueva orden
                    orderProduct.setProduct(product); // Asociar al producto
                    orderProduct.setQuantities(quantity); // Establecer la cantidad
                    orderProduct.setTotalAmount(product.getPriceProduct() * quantity); // Calcular el total por producto

                    newOrder.getOrderProducts().add(orderProduct); // Añadir el OrderProduct a la lista de la orden

                    // Crear el ProductDTO para el ticket
                    return new ProductDTO(product, quantity);
                })
                .collect(Collectors.toList());

        // Asignar el totalAmount calculado a la orden
        newOrder.setTotalAmount(totalAmount[0]);

        // Si es una orden de tipo DELIVERY, asignar la dirección
        if (purchaseRequestDTO.getOrderType() == OrderType.DELIVERY) {
            Adress deliveryAddress = clientService.getAddressById(purchaseRequestDTO.getAddressId());
            newOrder.setAdress(deliveryAddress);
        }

        // Guardar la orden en la base de datos
        orderService.saveOrder(newOrder);

        // Crear el DTO del ticket de la compra
        OrderTicketDTO orderTicketDTO;
        if (purchaseRequestDTO.getOrderType() == OrderType.DELIVERY) {
            orderTicketDTO = new OrderTicketDTO(productDTOList, "DELIVERY", address, totalAmount[0]);
        } else {
            orderTicketDTO = new OrderTicketDTO(productDTOList, purchaseRequestDTO.getOrderType().toString(), totalAmount[0]);
        }

        // Retornar el ticket con los productos, tipo de orden y el total
        return ResponseEntity.ok(orderTicketDTO);
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
