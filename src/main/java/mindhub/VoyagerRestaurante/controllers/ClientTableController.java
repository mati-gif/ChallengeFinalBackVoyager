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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
    public ResponseEntity<ClientTable> createClientTable(@RequestHeader("Authorization") String token,
                                                         @RequestBody ClientTableDTO clientTableDTO) {
        // Extraer el token sin el prefijo "Bearer "
        String jwtToken = token.substring(7);

        // Extraer el cliente autenticado desde el token
        String email = jwtUtilService.extractUsername(jwtToken);
        Client client = clientService.findByEmail(email);

        // Validar la mesa escogida
        Table table = tableService.getTableById(clientTableDTO.table().getId())
                .orElseThrow(() -> new RuntimeException("Table not found"));

        // Crear la nueva reserva (ClientTable)
        ClientTable clientTable = new ClientTable();
        clientTable.setClient(client); // El cliente autenticado
        clientTable.setTable(table); // La mesa elegida
        clientTable.setInitialDate(LocalDateTime.now()); // Fecha de inicio de la reserva
        clientTable.setFinalDate(LocalDateTime.now().plusHours(2)); // Ejemplo de duración de la reserva

        // Guardar la nueva reserva
        ClientTable newClientTable = clientTableService.saveClientTable(clientTable);

        return ResponseEntity.ok(newClientTable);
    }
}
