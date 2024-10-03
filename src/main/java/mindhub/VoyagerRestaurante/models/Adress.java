package mindhub.VoyagerRestaurante.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameStreet;
    private String zipCode;
    private String betweenStreets;
    private int streetNumber;
    private TypeHome typeHome;
    private int floorNumber;
    private String aparmentNumber;

    @OneToMany(mappedBy = "adress", fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    private Set<ClientAdress> clientAdress = new HashSet<>();

    public Adress(String nameStreet, String betweenStreets, int streetNumber, TypeHome typeHome, int floorNumber, String aparmentNumber, String zipCode) {
        this.nameStreet = nameStreet;
        this.betweenStreets = betweenStreets;
        this.streetNumber = streetNumber;
        this.typeHome = typeHome;
        this.floorNumber = floorNumber;
        this.aparmentNumber = aparmentNumber;
        this.zipCode = zipCode;
    }

    public Adress(String nameStreet, String betweenStreets, int streetNumber, TypeHome typeHome, String zipCode) {
        this.nameStreet = nameStreet;
        this.betweenStreets = betweenStreets;
        this.streetNumber = streetNumber;
        this.typeHome = typeHome;
        this.zipCode = zipCode;
    }

    public Adress() {
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

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public TypeHome getTypeHome() {
        return typeHome;
    }

    public void setTypeHome(TypeHome typeHome) {
        this.typeHome = typeHome;
    }

    public String getAparmentNumber() {
        return aparmentNumber;
    }

    public void setAparmentNumber(String aparmentNumber) {
        this.aparmentNumber = aparmentNumber;
    }

    public void addClientAdress(ClientAdress clientAdress) {
        clientAdress.setAdress(this);
        this.clientAdress.add(clientAdress);
    }
}
