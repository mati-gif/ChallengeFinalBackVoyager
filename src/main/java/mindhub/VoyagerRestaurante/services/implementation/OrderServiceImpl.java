package mindhub.VoyagerRestaurante.services.implementation;

import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.Order;
import mindhub.VoyagerRestaurante.repositories.OrderRepository;
import mindhub.VoyagerRestaurante.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Método para guardar una orden
    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Método para obtener todas las órdenes
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Método para eliminar una orden por su ID
    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // Método para obtener órdenes de un cliente
    @Override
    public List<Order> getOrdersByClient(Client client) {
        return orderRepository.findByClient(client);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
}

