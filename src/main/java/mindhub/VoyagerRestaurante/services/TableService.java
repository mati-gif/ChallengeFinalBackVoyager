package mindhub.VoyagerRestaurante.services;

import mindhub.VoyagerRestaurante.dtos.ClientTableDTO;
import mindhub.VoyagerRestaurante.dtos.TableApplicationDTO;
import mindhub.VoyagerRestaurante.dtos.TableDTO;
import mindhub.VoyagerRestaurante.models.Table;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface TableService {
    Table saveTable(Table table);
    ResponseEntity<?> saveTableE(TableApplicationDTO tableApplicationDTO, Authentication authentication);

    List<Table> getAllTables();

    List<TableDTO> getAllTablesDTO();

    Optional<Table> getTableById(Long id);

    void deleteTable(Long id);
}
