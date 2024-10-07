//package mindhub.VoyagerRestaurante.ServicesTests;
//
//import mindhub.VoyagerRestaurante.models.ClientTable;
//import mindhub.VoyagerRestaurante.repositories.ClientTableRepository;
//import mindhub.VoyagerRestaurante.services.implementation.ClientTableServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class ClientTableServiceTest {
//
//    @Mock
//    private ClientTableRepository clientTableRepository;
//
//    @InjectMocks
//    private ClientTableServiceImpl clientTableService;
//
//    private ClientTable reservation;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        reservation = new ClientTable(LocalDateTime.now(), LocalDateTime.now().plusHours(2));
//    }
//
//    @Test
//    void testSaveReservation() {
//        when(clientTableRepository.save(any(ClientTable.class))).thenReturn(reservation);
//
//        ClientTable savedReservation = clientTableService.saveClientTable(reservation);
//        assertEquals(reservation.getInitialDate(), savedReservation.getInitialDate());
//        verify(clientTableRepository, times(1)).save(reservation);
//    }
//
//    @Test
//    void testGetReservationById() {
//        when(clientTableRepository.findById(anyLong())).thenReturn(Optional.of(reservation));
//
//        Optional<ClientTable> foundReservation = clientTableService.getClientTableById(1L);
//        assertEquals(reservation.getInitialDate(), foundReservation.get().getInitialDate());
//        verify(clientTableRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void testDeleteReservation() {
//        clientTableService.deleteClientTable(1L);
//        verify(clientTableRepository, times(1)).deleteById(1L);
//    }
//}
