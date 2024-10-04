package mindhub.VoyagerRestaurante.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameStreet;
    private String zipCode;
    private String betweenStreets;
    private int streetNumber;

    @Enumerated(EnumType.STRING)  // Mapear el enum como String
    private TypeHome typeHome;

    @Column(nullable = true)  // Permitir que este campo sea nulo
    private Integer floorNumber;

    @Column(nullable = true)  // Permitir que este campo sea nulo
    private String aparmentNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Constructor para HOUSE
    public Adress(String nameStreet, String betweenStreets, int streetNumber, TypeHome typeHome, String zipCode) {
        this.nameStreet = nameStreet;
        this.betweenStreets = betweenStreets;
        this.streetNumber = streetNumber;
        this.typeHome = typeHome;
        this.zipCode = zipCode;
        this.floorNumber = null;  // Para una casa, no tiene sentido tener piso o departamento
        this.aparmentNumber = null;
    }

    // Constructor para APARTMENT
    public Adress(String nameStreet, String betweenStreets, int streetNumber, TypeHome typeHome, Integer floorNumber, String aparmentNumber, String zipCode) {
        this.nameStreet = nameStreet;
        this.betweenStreets = betweenStreets;
        this.streetNumber = streetNumber;
        this.typeHome = typeHome;
        this.floorNumber = floorNumber;
        this.aparmentNumber = aparmentNumber;
        this.zipCode = zipCode;
    }

    public Adress() {
    }

    public TypeHome getTypeHome() {
        return typeHome;
    }

    public void setTypeHome(TypeHome typeHome) {
        this.typeHome = typeHome;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getAparmentNumber() {
        return aparmentNumber;
    }

    public void setAparmentNumber(String aparmentNumber) {
        this.aparmentNumber = aparmentNumber;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
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
}