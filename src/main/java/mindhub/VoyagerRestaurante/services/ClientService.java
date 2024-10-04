package mindhub.VoyagerRestaurante.services;

import mindhub.VoyagerRestaurante.dtos.ClientDTO;
import mindhub.VoyagerRestaurante.dtos.RegisterDTO;
import mindhub.VoyagerRestaurante.models.Adress;
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
    String encodedPassword(RegisterDTO registerDTO);
    List<ClientDTO> getAllClientDto();
    ClientDTO getClientDto(Client client);
    List<Client> getAllClient();
    Client createNewClient(RegisterDTO registerDTO);
    Adress getAddressById(Long addressId);

}
