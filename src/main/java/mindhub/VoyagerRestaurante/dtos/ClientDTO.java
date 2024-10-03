package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.Client;

import java.util.List;
import java.util.Optional;

public class ClientDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> phoneNumbers;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.phoneNumbers = client.getPhoneNumbers();
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
