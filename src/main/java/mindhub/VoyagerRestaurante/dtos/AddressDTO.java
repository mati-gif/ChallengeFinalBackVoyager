package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.Adress;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.TypeHome;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AddressDTO {
    private Long id;
    private String nameStreet;
    private String zipCode;
    private String betweenStreets;
    private int streetNumber;
    private TypeHome typeHome;
    private int floorNumber;
    private String aparmentNumber;
//    private List<ClientDTO> clients;

    public AddressDTO(Adress adress) {
        this.id = adress.getId();
        this.nameStreet = adress.getNameStreet();
        this.zipCode = adress.getZipCode();
        this.betweenStreets = adress.getBetweenStreets();
        this.streetNumber = adress.getStreetNumber();
        this.typeHome = adress.getTypeHome();
        this.floorNumber = adress.getFloorNumber();
        this.aparmentNumber = adress.getAparmentNumber();

//        this.clients = converClientsToDto(adress.getClients());
    }

//    private List<ClientDTO> converClientsToDto(List<Client> clients) {
//        return clients.stream()
//                .map(ClientDTO::new)
//                .collect(Collectors.toList());
//    }

    public Long getId() {
        return id;
    }

    public String getNameStreet() {
        return nameStreet;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getBetweenStreets() {
        return betweenStreets;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public TypeHome getTypeHome() {
        return typeHome;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public String getAparmentNumber() {
        return aparmentNumber;
    }

//    public List<ClientDTO> getClients() {
//        return clients;
//    }
}
