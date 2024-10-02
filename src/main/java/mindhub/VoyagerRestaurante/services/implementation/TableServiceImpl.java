package mindhub.VoyagerRestaurante.services.implementation;

import mindhub.VoyagerRestaurante.models.Table;
import mindhub.VoyagerRestaurante.repositories.TableRepository;
import mindhub.VoyagerRestaurante.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableRepository tableRepository;

    @Override
    public Table saveTable(Table table) {
        return tableRepository.save(table);
    }

    @Override
    public List<Table> getAllTables() {
        return tableRepository.findAll();
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
