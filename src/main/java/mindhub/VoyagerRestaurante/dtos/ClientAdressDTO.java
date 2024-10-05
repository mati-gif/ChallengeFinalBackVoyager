package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.Adress;
import mindhub.VoyagerRestaurante.models.ClientAdress;
import mindhub.VoyagerRestaurante.models.TypeHome;

public class ClientAdressDTO {
    private Long id;
    private String nameStreet;
    private String zipCode;
    private String betweenStreets;
    private int streetNumber;
    private TypeHome typeHome;
    private Integer floorNumber;  // Para permitir null
    private String aparmentNumber;

    public ClientAdressDTO() {
    }

    // Constructor que recibe ClientAdress
    public ClientAdressDTO(ClientAdress clientAdress) {
        Adress adress = clientAdress.getAdress();  // Obtén la dirección de ClientAdress
        this.id = adress.getId();
        this.nameStreet = adress.getNameStreet();
        this.zipCode = adress.getZipCode();
        this.betweenStreets = adress.getBetweenStreets();
        this.streetNumber = adress.getStreetNumber();
        this.typeHome = adress.getTypeHome();

        if (adress.getTypeHome() == TypeHome.APARTMENT) {
            this.floorNumber = adress.getFloorNumber();
            this.aparmentNumber = adress.getAparmentNumber();
        }
    }

    // Getters y setters
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

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public String getAparmentNumber() {
        return aparmentNumber;
    }
}
