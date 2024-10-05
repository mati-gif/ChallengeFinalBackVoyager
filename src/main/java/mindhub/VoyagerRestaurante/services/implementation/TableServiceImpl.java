package mindhub.VoyagerRestaurante.services.implementation;

import mindhub.VoyagerRestaurante.dtos.ClientTableDTO;
import mindhub.VoyagerRestaurante.dtos.ProductDTO;
import mindhub.VoyagerRestaurante.dtos.TableApplicationDTO;
import mindhub.VoyagerRestaurante.dtos.TableDTO;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.ClientTable;
import mindhub.VoyagerRestaurante.models.Table;
import mindhub.VoyagerRestaurante.repositories.ClientRepository;
import mindhub.VoyagerRestaurante.repositories.ClientTableRepository;
import mindhub.VoyagerRestaurante.repositories.TableRepository;
import mindhub.VoyagerRestaurante.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private ClientTableRepository clientTableRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ResponseEntity<?> saveTableE(TableApplicationDTO tableApplicationDTO, Authentication authentication) {
        Table table = tableRepository.findById(tableApplicationDTO.id()).orElseThrow();
        Client client = clientRepository.findByEmail(authentication.getName());
        ClientTable clientTable= new ClientTable(LocalDateTime.now(), LocalDateTime.now().plusHours(1), client, table);
        client.addClientTable(clientTable);
        table.addClientTable(clientTable);
        clientTableRepository.save(clientTable);
        return  new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public Table saveTable(Table table) {
        return tableRepository.save(table);
    }

    @Override
    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    @Override
    public List<TableDTO> getAllTablesDTO() {
        return getAllTables().stream()
                .map(TableDTO::new)
                .collect(Collectors.toList());
    }



    @Override
    public Optional<Table> getTableById(Long id) {
        return tableRepository.findById(id);
    }

    @Override
    public void deleteTable(Long id) {
        tableRepository.deleteById(id);
    }
}
