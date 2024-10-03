package mindhub.VoyagerRestaurante.services.implementation;

import mindhub.VoyagerRestaurante.dtos.ClientDTO;
import mindhub.VoyagerRestaurante.dtos.RegisterDTO;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.repositories.ClientRepository;
import mindhub.VoyagerRestaurante.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email); // Retornar Optional
    }

    @Override
    public List<Client> getAllClient() {
        return clientRepository.findAll();
    }


    @Override
    public List<ClientDTO> getAllClientDto() {
        return getAllClient().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientDto(Client client) {
        return new ClientDTO(client);
    }
    //Este método solo convierte un objeto Client en un objeto ClientDto.
    //No interactúa con la base de datos.
    //Es útil cuando quieres transformar un cliente en un DTO sin modificar ni guardar el cliente en la base de datos.

    @Override
    public String encodedPassword(RegisterDTO registerDTO) {

        return passwordEncoder.encode(registerDTO.password());
    }

    @Override
    public Client createNewClient(RegisterDTO registerDTO) {
        // Codifica la contraseña del cliente
        String encodedPassword = encodedPassword(registerDTO);

        // Crea un nuevo objeto Client con los datos proporcionados
        return new Client(registerDTO.firstName(), registerDTO.lastName(), registerDTO.email(), encodedPassword, registerDTO.phoneNumbers()) ;
    }

}


