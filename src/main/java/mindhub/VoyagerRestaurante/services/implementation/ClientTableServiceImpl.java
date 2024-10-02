package mindhub.VoyagerRestaurante.services.implementation;

import mindhub.VoyagerRestaurante.models.ClientTable;
import mindhub.VoyagerRestaurante.repositories.ClientTableRepository;
import mindhub.VoyagerRestaurante.services.ClientTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientTableServiceImpl implements ClientTableService {

    @Autowired
    private ClientTableRepository clientTableRepository;

    @Override
    public ClientTable saveClientTable(ClientTable clientTable) {
        return clientTableRepository.save(clientTable);
    }

    @Override
    public List<ClientTable> getAllClientTables() {
        return clientTableRepository.findAll();
    }

    @Override
    public Optional<ClientTable> getClientTableById(Long id) {
        return clientTableRepository.findById(id);
    }

    @Override
    public void deleteClientTable(Long id) {
        clientTableRepository.deleteById(id);
    }
}
