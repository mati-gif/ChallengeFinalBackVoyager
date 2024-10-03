package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.Adress;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.ClientAdress;
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
    private List<OrderDTO> orders; // Changed to orders for consistency

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.phoneNumbers = client.getPhoneNumbers();
        this.address = client.getClientAdress().stream()
                .map(ClientAdressDTO::new)
                .collect(Collectors.toList());
        this.orders = client.getOrders().stream() // Changed to orders for consistency
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }

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
}
