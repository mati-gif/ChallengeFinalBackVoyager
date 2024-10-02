package mindhub.VoyagerRestaurante.ServicesTests;

import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.repositories.ClientRepository;
import mindhub.VoyagerRestaurante.services.implementation.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        client = new Client("John", "Doe", "john.doe@example.com", "password123", null);
    }

    @Test
    void testSaveClient() {
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client savedClient = clientService.saveClient(client);
        assertEquals(client.getEmail(), savedClient.getEmail());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testGetClientById() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        Optional<Client> foundClient = clientService.getClientById(1L);
        assertEquals(client.getEmail(), foundClient.get().getEmail());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteClient() {
        clientService.deleteClient(1L);
        verify(clientRepository, times(1)).deleteById(1L);
    }
}

