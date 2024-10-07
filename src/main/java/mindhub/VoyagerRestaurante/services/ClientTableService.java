package mindhub.VoyagerRestaurante.services;

import mindhub.VoyagerRestaurante.models.ClientTable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ClientTableService {
    ClientTable saveClientTable(ClientTable clientTable);
    Set<ClientTable> getAllClientTables();
}

