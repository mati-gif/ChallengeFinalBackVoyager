package mindhub.VoyagerRestaurante.controllers;

import mindhub.VoyagerRestaurante.dtos.ClientTableDTO;
import mindhub.VoyagerRestaurante.dtos.TableApplicationDTO;
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
    public ResponseEntity<?> createClientTable(Authentication authentication, @RequestBody TableApplicationDTO tableApplicationDTO) {
        // Obtener el email del usuario autenticado
        String email = authentication.getName();

        // Obtener el cliente autenticado
        Client authenticatedClient = clientService.findByEmail(email);
        if (authenticatedClient == null) {
            return ResponseEntity.badRequest().body("Client not found");
        }

        // Obtener la mesa por su ID
        Table table = tableService.getTableById(tableApplicationDTO.id()).orElseThrow(null);
        if (table == null) {
            return ResponseEntity.badRequest().body("Table not found");
        }

        return tableService.saveTableE(tableApplicationDTO, authentication);
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
