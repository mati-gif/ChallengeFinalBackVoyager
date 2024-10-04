package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.Adress;
import mindhub.VoyagerRestaurante.models.TypeHome;

public class AddressDTO {
    private Long id;
    private String nameStreet;
    private String zipCode;
    private String betweenStreets;
    private int streetNumber;
    private TypeHome typeHome;
    private int floorNumber;
    private String aparmentNumber;

    // Constructor por defecto necesario para la deserialización de JSON
    public AddressDTO() {
    }

    // Constructor que inicializa los campos desde la entidad Adress
    public AddressDTO(Adress adress) {
        this.id = adress.getId();
        this.nameStreet = adress.getNameStreet();
        this.zipCode = adress.getZipCode();
        this.betweenStreets = adress.getBetweenStreets();
        this.streetNumber = adress.getStreetNumber();
        this.typeHome = adress.getTypeHome();
        this.floorNumber = adress.getFloorNumber();
        this.aparmentNumber = adress.getAparmentNumber();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameStreet() {
        return nameStreet;
    }

    public void setNameStreet(String nameStreet) {
        this.nameStreet = nameStreet;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getBetweenStreets() {
        return betweenStreets;
    }

    public void setBetweenStreets(String betweenStreets) {
        this.betweenStreets = betweenStreets;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public TypeHome getTypeHome() {
        return typeHome;
    }

    public void setTypeHome(TypeHome typeHome) {
        this.typeHome = typeHome;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getAparmentNumber() {
        return aparmentNumber;
    }

    public void setAparmentNumber(String aparmentNumber) {
        this.aparmentNumber = aparmentNumber;
    }
}
