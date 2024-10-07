package mindhub.VoyagerRestaurante.controllers;

import mindhub.VoyagerRestaurante.dtos.ClientReservDTO;
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
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> createClientTable(Authentication authentication, @RequestBody ClientReservDTO clientReservDTO) {
        try {
            // Obtener el email del usuario autenticado
            String email = authentication.getName();

            // Obtener el cliente autenticado
            Client authenticatedClient = clientService.findByEmail(email);
            if (authenticatedClient == null) {
                return ResponseEntity.badRequest().body("Client not found");
            }

            // Verificar que el ID de la mesa no sea nulo antes de buscar la mesa
            if (clientReservDTO.tableId() == null) {
                return ResponseEntity.badRequest().body("Table ID cannot be null");
            }

            // Obtener la mesa por su ID
            Optional<Table> optionalTable = tableService.getTableById(clientReservDTO.tableId());
            if (optionalTable.isEmpty()) {
                return ResponseEntity.badRequest().body("Table not found");
            }

            Table table = optionalTable.get();

            // Verificar si la mesa está libre
            if (table.getState() != TableStatus.FREE) {
                return ResponseEntity.badRequest().body("Table is already reserved or busy");
            }

            // Crear la nueva reserva
            ClientTable clientTable = new ClientTable(LocalDateTime.parse(clientReservDTO.initialReservTime()));
            authenticatedClient.addClientTable(clientTable);
            table.addClientTable(clientTable);

            // Guardar la reserva
            clientTableService.saveClientTable(clientTable);

            // Retornar una respuesta exitosa con el objeto de reserva
            return ResponseEntity.status(HttpStatus.CREATED).body("Reservation created successfully");

        } catch (Exception e) {
            // Manejar la excepción y devolver un mensaje de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the reservation: " + e.getMessage());
        }
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
