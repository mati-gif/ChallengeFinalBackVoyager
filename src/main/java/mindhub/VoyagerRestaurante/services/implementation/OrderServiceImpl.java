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

    @Override
    public List<Order> getOrdersByClient(Client client) {
        return orderRepository.findByClient(client);
    }
}
