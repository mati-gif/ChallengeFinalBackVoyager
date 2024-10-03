package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.Adress;
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
    private List<AddressDTO> addressDTOS;
    private List<Order> order;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.phoneNumbers = client.getPhoneNumbers();
//        this.addressDTOS = client.getAdress().stream().map(adress -> new AddressDTO(adress)).collect(Collectors.toList());
        this.order = client.getOrders();
    }

    public ClientDTO(Optional<Client> byEmail) {
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

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public String getEmail() {
        return email;
    }
}
