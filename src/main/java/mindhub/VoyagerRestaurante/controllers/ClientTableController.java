package mindhub.VoyagerRestaurante.controllers;

import mindhub.VoyagerRestaurante.dtos.ClientTableDTO;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.ClientTable;
import mindhub.VoyagerRestaurante.models.Table;
import mindhub.VoyagerRestaurante.models.TableStatus;
import mindhub.VoyagerRestaurante.serviceSecurity.JwtUtilService;
import mindhub.VoyagerRestaurante.services.ClientService;
import mindhub.VoyagerRestaurante.services.ClientTableService;
import mindhub.VoyagerRestaurante.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientTables")
public class ClientTableController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private TableService tableService;

    @Autowired
    private ClientTableService clientTableService;

    @Autowired
    private JwtUtilService jwtUtilService;

    // Crear una nueva reserva de mesa (ClientTable)
    @PostMapping("/create")
    public ResponseEntity<?> createClientTable(Authentication authentication, @RequestBody ClientTableDTO clientTableDTO) {
        // Obtener el email del usuario autenticado
        String email = authentication.getName();

        // Obtener el cliente autenticado
        Client authenticatedClient = clientService.findByEmail(email);
        if (authenticatedClient == null) {
            return ResponseEntity.badRequest().body("Client not found");
        }

        // Verificar que el ID de la mesa no sea nulo antes de buscar la mesa
        if (clientTableDTO.getTableId() == null) {
            return ResponseEntity.badRequest().body("Table ID cannot be null");
        }

        // Obtener la mesa por su ID
        Optional<Table> optionalTable = tableService.getTableById(clientTableDTO.getTableId());
        if (optionalTable.isEmpty()) {
            return ResponseEntity.badRequest().body("Table not found");
        }

        Table table = optionalTable.get();

        // Verificar si la mesa está libre
        if (table.getState() != TableStatus.FREE) {
            return ResponseEntity.badRequest().body("Table is already reserved or busy");
        }

        // Crear la nueva reserva
        ClientTable clientTable = new ClientTable();
        clientTable.setClient(authenticatedClient);
        clientTable.setTable(table);
        clientTable.setInitialDate(clientTableDTO.getReservationStart());
        clientTable.setFinalDate(clientTableDTO.getReservationStart().plusMinutes(90)); // Duración de 90 minutos

        // Cambiar el estado de la mesa a RESERVED
        table.setState(TableStatus.RESERVED);
        tableService.saveTable(table);

        // Guardar la reserva
        ClientTable newClientTable = clientTableService.saveClientTable(clientTable);

        // Crear un DTO con los detalles de la reserva y la mesa
//        ClientTableDTO responseDTO = new ClientTableDTO(
//                newClientTable.getTable().getId(),
//                newClientTable.getTable().getTableNumber(),
//                newClientTable.getTable().getCapacity(),
//                newClientTable.getTable().getSectorType(),
//                newClientTable.getTable().getState(),
//                newClientTable.getInitialDate(),
//                newClientTable.getFinalDate()
//        );
        ClientTableDTO responseDTO = new ClientTableDTO(clientTable);

        // Respuesta con los detalles de la reserva
        return ResponseEntity.ok(responseDTO);
    }


    // Obtener las reservas del cliente autenticado
    @GetMapping("/myReservations")
    public ResponseEntity<?> getClientReservations(Authentication authentication) {
        String email = authentication.getName();
        Client client = clientService.findByEmail(email);
        if (client == null) {
            return ResponseEntity.badRequest().body("Client not found");
        }

        Set<ClientTableDTO> clientReservations = client.getClientTables()
                .stream()
                .map(reservation -> new ClientTableDTO(reservation))
                .collect(Collectors.toSet());
//                .map(reservation -> new ClientTableDTO(
//                        reservation.getTable().getId(),
//                        reservation.getTable().getTableNumber(),
//                        reservation.getTable().getCapacity(),
//                        reservation.getTable().getSectorType(),
//                        reservation.getTable().getState(),
//                        reservation.getInitialDate(),
//                        reservation.getFinalDate()  // Añadir fecha de fin
//                ))
//                .collect(Collectors.toSet());

        return ResponseEntity.ok(clientReservations);
    }


    // Obtener todas las reservas
    @GetMapping("/allReservations")
    public ResponseEntity<?> getAllReservations() {
        Set<ClientTableDTO> allReservations = clientTableService.getAllClientTables()
                .stream()
                .map(reservation -> new ClientTableDTO(reservation))
                .collect(Collectors.toSet());
//                .map(reservation -> new ClientTableDTO(
//                        reservation.getTable().getId(),
//                        reservation.getTable().getTableNumber(),
//                        reservation.getTable().getCapacity(),
//                        reservation.getTable().getSectorType(),
//                        reservation.getTable().getState(),
//                        reservation.getInitialDate(),
//                        reservation.getFinalDate()  // Añadir fecha de fin
//                ))
//                .collect(Collectors.toSet());

        return ResponseEntity.ok(allReservations);
    }

}
