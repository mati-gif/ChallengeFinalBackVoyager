package mindhub.VoyagerRestaurante.services.implementation;

import mindhub.VoyagerRestaurante.models.ClientTable;
import mindhub.VoyagerRestaurante.repositories.ClientTableRepository;
import mindhub.VoyagerRestaurante.services.ClientTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClientTableServiceImpl implements ClientTableService {

    @Autowired
    private ClientTableRepository clientTableRepository;

    @Override
    public ClientTable saveClientTable(ClientTable clientTable) {
        return clientTableRepository.save(clientTable);
    }

    @Override
    public Set<ClientTable> getAllClientTables() {
        return new HashSet<>(clientTableRepository.findAll());
    }
}

