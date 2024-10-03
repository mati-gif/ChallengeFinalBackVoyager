package mindhub.VoyagerRestaurante.services;

import mindhub.VoyagerRestaurante.dtos.ClientDTO;
import mindhub.VoyagerRestaurante.models.Client;
import org.apache.catalina.LifecycleState;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client saveClient(Client client);

    List<Client> getAllClients();

    Optional <Client> getClientById(Long id);

    void deleteClient(Long id);

    Client findByEmail(String email);

    List<ClientDTO> getAllClientDto();
    ClientDTO getClientDto(Client client);
    List<Client> getAllClient();

}
