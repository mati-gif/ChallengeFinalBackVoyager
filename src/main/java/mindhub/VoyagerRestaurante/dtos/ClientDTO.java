package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.Order;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> phoneNumbers;
    private List<ClientAdressDTO> address;
    private List<OrderDTO> orders;
    private List<ClientTableDTO> reservas;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.phoneNumbers = client.getPhoneNumbers();
        this.address = client.getClientAdress().stream().map(ClientAdressDTO::new).collect(Collectors.toList());
        this.orders = client.getOrders().stream().map(OrderDTO::new).collect(Collectors.toList());
        this.reservas = client.getClientTables().stream().map(ClientTableDTO::new).collect(Collectors.toList()); // Esto ya funciona correctamente
    }

    public ClientDTO(Optional<Client> byEmail) {
        // Este constructor opcional necesita implementación si se utiliza en algún lugar
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public List<ClientAdressDTO> getAddress() {
        return address;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public List<ClientTableDTO> getReservas() {
        return reservas;
    }
}
