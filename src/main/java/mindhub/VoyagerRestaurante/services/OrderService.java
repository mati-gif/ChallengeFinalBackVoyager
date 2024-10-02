package mindhub.VoyagerRestaurante.services;

import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrdersByClient(Client client);
}
