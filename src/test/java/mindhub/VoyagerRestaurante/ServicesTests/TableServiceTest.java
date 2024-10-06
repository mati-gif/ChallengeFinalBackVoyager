//package mindhub.VoyagerRestaurante.ServicesTests;
//
//import mindhub.VoyagerRestaurante.models.Table;
//import mindhub.VoyagerRestaurante.repositories.TableRepository;
//import mindhub.VoyagerRestaurante.services.implementation.TableServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class TableServiceTest {
//
//    @Mock
//    private TableRepository tableRepository;
//
//    @InjectMocks
//    private TableServiceImpl tableService;
//
//    private Table table;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        table = new Table(true, 4, null);
//    }
//
//    @Test
//    void testSaveTable() {
//        when(tableRepository.save(any(Table.class))).thenReturn(table);
//
//        Table savedTable = tableService.saveTable(table);
//        assertEquals(table.isState(), savedTable.isState());
//        verify(tableRepository, times(1)).save(table);
//    }
//
//    @Test
//    void testGetAllTables() {
//        List<Table> tables = List.of(table);
//        when(tableRepository.findAll()).thenReturn(tables);
//
//        List<Table> foundTables = tableService.getAllTables();
//        assertEquals(1, foundTables.size());
//        verify(tableRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testGetTableById() {
//        when(tableRepository.findById(anyLong())).thenReturn(Optional.of(table));
//
//        Optional<Table> foundTable = tableService.getTableById(1L);
//        assertEquals(table.isState(), foundTable.get().isState());
//        verify(tableRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void testDeleteTable() {
//        tableService.deleteTable(1L);
//        verify(tableRepository, times(1)).deleteById(1L);
//    }
//}
//
