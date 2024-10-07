package mindhub.VoyagerRestaurante.services;

import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.Order;
import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);  // Método para guardar una orden
    List<Order> getAllOrders();    // Método para obtener todas las órdenes
    void deleteOrder(Long id);     // Método para eliminar una orden por su ID
    List<Order> getOrdersByClient(Client client);  // Método para obtener órdenes por cliente
}
