package mindhub.VoyagerRestaurante.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @ElementCollection
    private List<String> phoneNumbers = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ClientAdress> clientAdress = new HashSet<>();

//    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    @JsonBackReference  // No serializará la relación inversa cuando serialices un Client
    private List<ClientTable> clientTables = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ReviewClientProduct> reviews = new ArrayList<>();

    public Client() {
    }

    public Client(String firstName, String lastName, String email, String password, List<String> phoneNumbers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumbers = phoneNumbers;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Set<ClientAdress> getClientAdress() {
        return clientAdress;
    }

    public List<ClientTable> getClientTables() {
        return clientTables;
    }


    public List<ReviewClientProduct> getReviews() {
        return reviews;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }


    public void addClientAdress(ClientAdress clientAdress) {
        clientAdress.setClient(this); // Set the client reference in ClientAdress
        this.clientAdress.add(clientAdress); // Add to the client's addresses
    }

    public void addOrder(Order order) {
        order.setClient(this);
        this.orders.add(order);
    }

    public void addClientTable (ClientTable clientTable){
        clientTable.setClient(this);
        this.clientTables.add(clientTable);
    }

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String firstName;
//    private String lastName;
//    private String email;
//    private String password;
//
//    @ElementCollection
//    private List<String> phoneNumbers = new ArrayList<>();
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "adress_id")
//    @JsonBackReference // Evita la recursividad inversa hacia Adress
//    private Adress adress;
//
//    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JsonBackReference  // No serializará la relación inversa cuando serialices un Client
//    private List<ClientTable> clientTables = new ArrayList<>();
//
//    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<Order> orders = new ArrayList<>();
//
//    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<ReviewClientProduct> reviews = new ArrayList<>();
//
//    public Client() {
//    }
//
//    public Client(String firstName, String lastName, String email, String password, List<String> phoneNumbers) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.password = password;
//        this.phoneNumbers = phoneNumbers;
//    }
//
//    // Getters y setters
//    public Long getId() {
//        return id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public List<String> getPhoneNumbers() {
//        return phoneNumbers;
//    }
//
//    public void setPhoneNumbers(List<String> phoneNumbers) {
//        this.phoneNumbers = phoneNumbers;
//    }
//
//    public Adress getAdress() {
//        return adress;
//    }
//
//    public void setAdress(Adress adress) {
//        this.adress = adress;
//    }
//
//    public void addClientTable (ClientTable clientTable){
//        clientTable.setClient(this);
//        clientTables.add(clientTable);
//    }
}
