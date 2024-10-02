package mindhub.VoyagerRestaurante.repositories;

import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClient(Client client);  // Obtener todas las órdenes de un cliente
}


