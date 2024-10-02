package mindhub.VoyagerRestaurante.services;

import mindhub.VoyagerRestaurante.models.Client;
import org.apache.catalina.LifecycleState;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client saveClient(Client client);

    List<Client> getAllClients();

    Optional <Client> getClientById(Long id);

    void deleteClient(Long id);

    Optional<Client> findByEmail(String email);

}
