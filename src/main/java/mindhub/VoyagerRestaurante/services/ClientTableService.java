package mindhub.VoyagerRestaurante.services;

import mindhub.VoyagerRestaurante.models.ClientTable;
import java.util.List;
import java.util.Optional;

public interface ClientTableService {
    ClientTable saveClientTable(ClientTable clientTable);

    List<ClientTable> getAllClientTables();

    Optional<ClientTable> getClientTableById(Long id);

    void deleteClientTable(Long id);
}
