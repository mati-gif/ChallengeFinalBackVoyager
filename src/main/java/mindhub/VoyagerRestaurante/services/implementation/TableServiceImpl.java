package mindhub.VoyagerRestaurante.services.implementation;

import mindhub.VoyagerRestaurante.dtos.*;
import mindhub.VoyagerRestaurante.models.*;
import mindhub.VoyagerRestaurante.repositories.ClientRepository;
import mindhub.VoyagerRestaurante.repositories.ClientTableRepository;
import mindhub.VoyagerRestaurante.repositories.TableRepository;
import mindhub.VoyagerRestaurante.services.ClientService;
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
    @Autowired
    private ClientService clientService;

    @Override
    public ResponseEntity<?> saveTableE(TableApplicationDTO tableApplicationDTO, Authentication authentication) {
        Table table = tableRepository.findById(tableApplicationDTO.id()).orElseThrow();
        Client client = clientRepository.findByEmail(authentication.getName());
//        ClientTable clientTable= new ClientTable(LocalDateTime.now(), LocalDateTime.now().plusHours(1), client, table);
        // Convertir el String en un LocalDateTime --> "2024-10-10T22:00:00"
        LocalDateTime dateTime = LocalDateTime.parse(tableApplicationDTO.localDateTime());
        ClientTable clientTable = new ClientTable(dateTime);
        client.addClientTable(clientTable);
        table.addClientTable(clientTable);
        clientTableRepository.save(clientTable);
        return  new ResponseEntity<>("Successful reservation.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> creatNewTable(Authentication authentication, DTOTableRecord dtoTableRecord) {
        Client client = clientService.findByEmail(authentication.getName());
        if (client == null) {
            return new ResponseEntity<>("You need be logged.",HttpStatus.FORBIDDEN);
        }
        SectorType sectorType = SectorType.GROUND_FLOOR;
        if (dtoTableRecord.sectorType().equalsIgnoreCase("GROUND_FLOOR")) {
            sectorType = SectorType.GROUND_FLOOR;
        }
        if (dtoTableRecord.sectorType().equalsIgnoreCase("FIRST_FLOOR")) {
            sectorType = SectorType.FIRST_FLOOR;
        }
        if (dtoTableRecord.sectorType().equalsIgnoreCase("OUTDOOR")) {
            sectorType = SectorType.OUTDOOR;
        }
        Table newTable = new Table(dtoTableRecord.cpacity(), sectorType, dtoTableRecord.cpacity(), TableStatus.FREE);
        return new ResponseEntity<>("Table created", HttpStatus.OK);
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
