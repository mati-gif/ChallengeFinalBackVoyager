package mindhub.VoyagerRestaurante.controllers;

import mindhub.VoyagerRestaurante.dtos.TableApplicationDTO;
import mindhub.VoyagerRestaurante.dtos.TableDTO;
import mindhub.VoyagerRestaurante.models.Table;
import mindhub.VoyagerRestaurante.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    @Autowired
    private TableService tableService;

    // Crear una nueva mesa
    @PostMapping("/create")
    public ResponseEntity<?> createTable(@RequestBody TableApplicationDTO tableApplicationDTO, Authentication authentication) {
        return tableService.saveTableE(tableApplicationDTO, authentication);

    }

    // Obtener todas las mesas
    @GetMapping("/")
    public List<TableDTO> getAllTables() {
        return tableService.getAllTablesDTO();
    }

    // Obtener una mesa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Table> getTableById(@PathVariable Long id) {
        Optional<Table> table = tableService.getTableById(id);
        return table.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una mesa por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable Long id) {
        tableService.deleteTable(id);
        return ResponseEntity.noContent().build();
    }
}

