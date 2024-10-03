package mindhub.VoyagerRestaurante.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameStreet;  //peru
    private String zipCode;     //40107
    private String betweenStreets;  //quntana y belgrano
    private int streetNumber;   // 5000
    private TypeHome typeHome; //Casa
    private int floorNumber;
    private String aparmentNumber;


    @OneToMany( mappedBy = "adress",fetch = FetchType.EAGER)
    @JsonManagedReference // Esta parte de la relación sí se serializa
    private List<Client> clients = new ArrayList<>();

    public Adress(String nameStreet, String betweenStreets, int streetNumber, TypeHome typeHome ,int floorNumber, String aparmentNumber, String zipCode) {
        this.nameStreet = nameStreet;
        this.betweenStreets = betweenStreets;
        this.streetNumber = streetNumber;
        this.typeHome = typeHome;
        this.floorNumber = floorNumber;
        this.aparmentNumber = aparmentNumber;
        this.zipCode = zipCode;
    }

    public Adress(String nameStreet, String betweenStreets, int streetNumber, TypeHome typeHome, String zipCode){
        this.nameStreet = nameStreet;
        this.betweenStreets = betweenStreets;
        this.streetNumber = streetNumber;
        this.typeHome = typeHome;
        this.zipCode = zipCode;
    }

    public Adress() {
    }


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

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

}


