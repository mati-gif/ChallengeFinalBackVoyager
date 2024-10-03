package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.ClientAdress;
import mindhub.VoyagerRestaurante.models.TypeHome;

public class ClientAdressDTO {
    private Long id;
    private String nameStreet;
    private String zipCode;
    private String betweenStreets;
    private int streetNumber;
    private TypeHome typeHome;
    private Integer floorNumber; // Usar Integer para poder ser null
    private String apartmentNumber; // Usar String para poder ser null

    // Constructor que inicializa todos los campos
    public ClientAdressDTO(ClientAdress clientAdress) {
        this.id = clientAdress.getAdress().getId();
        this.nameStreet = clientAdress.getAdress().getNameStreet();
        this.zipCode = clientAdress.getAdress().getZipCode();
        this.betweenStreets = clientAdress.getAdress().getBetweenStreets();
        this.streetNumber = clientAdress.getAdress().getStreetNumber();
        this.typeHome = clientAdress.getAdress().getTypeHome();

        // Verificar el tipo de hogar y asignar solo si es necesario
        if (TypeHome.APARTMENT.equals(this.typeHome)) {
            this.floorNumber = clientAdress.getAdress().getFloorNumber();
            this.apartmentNumber = clientAdress.getAdress().getAparmentNumber();
        } else {
            this.floorNumber = null; // o puedes establecer un valor por defecto
            this.apartmentNumber = null; // o puedes establecer un valor por defecto
        }
    }

    // Getters y setters (opcional)
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

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNameStreet(String nameStreet) {
        this.nameStreet = nameStreet;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setBetweenStreets(String betweenStreets) {
        this.betweenStreets = betweenStreets;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setTypeHome(TypeHome typeHome) {
        this.typeHome = typeHome;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }
}
