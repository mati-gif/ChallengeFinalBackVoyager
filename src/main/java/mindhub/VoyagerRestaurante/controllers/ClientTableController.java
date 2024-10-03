package mindhub.VoyagerRestaurante.controllers;

import mindhub.VoyagerRestaurante.dtos.ClientTableDTO;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.ClientTable;
import mindhub.VoyagerRestaurante.models.Table;
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

        // Obtener los clientes por sus IDs
        Set<Client> clients = clientTableDTO.clientIds().stream()
                .map(clientService::getClientById)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());

        if (clients.size() != clientTableDTO.clientIds().size()) {
            return ResponseEntity.badRequest().body("Some clients not found");
        }

        // Obtener la mesa por su ID
        Optional<Table> optionalTable = tableService.getTableById(clientTableDTO.tableId());
        if (optionalTable.isEmpty()) {
            return ResponseEntity.badRequest().body("Table not found");
        }

        // Extraer el cliente autenticado desde el token
        String emaill = authenticatedClient.getEmail();
        Client client = clientService.findByEmail(email);


        Table table = optionalTable.get();

        // Verificar si la mesa ya ha sido reservada o si está ocupada
        if (!table.isState()) {
            return ResponseEntity.badRequest().body("Table is already reserved");
        }

        // Verificar que la capacidad de la mesa sea suficiente
        if (clients.size() > table.getCapacity()) {
            return ResponseEntity.badRequest().body("Too many clients for this table's capacity");
        }

        // Verificar que el sector seleccionado coincida con el sector de la mesa
        if (table.getSectorType() != clientTableDTO.sectorType()) {
            return ResponseEntity.badRequest().body("Sector mismatch");
        }

        // Crear la nueva reserva
        ClientTable clientTable = new ClientTable();

        clientTable.setInitialDate(clientTableDTO.reservationStart()); // Fecha de inicio
        clientTable.setFinalDate(clientTableDTO.reservationStart().plusMinutes(90)); // Fecha de fin (+1 hora 30 minutos)

        // Asignar la mesa y los clientes
        clientTable.setTable(table);
        clients.forEach(clientt -> clientTable.setClient(client));

        // Marcar la mesa como ocupada (false = reservada)
        table.setState(false);

        clientTable.setClient(client); // El cliente autenticado
        clientTable.setTable(table); // La mesa elegida
        clientTable.setInitialDate(LocalDateTime.now()); // Fecha de inicio de la reserva
        clientTable.setFinalDate(LocalDateTime.now().plusHours(2)); // Ejemplo de duración de la reserva


        // Guardar la reserva
        ClientTable newClientTable = clientTableService.saveClientTable(clientTable);

        // Crear una respuesta con los detalles de la reserva
        return ResponseEntity.ok(newClientTable);
    }
}
