package mindhub.VoyagerRestaurante.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Column(name = "phoneNumber")
    private List<String> phoneNumbers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adress_id")// No serializa esta relación en el lado del cliente
    private Adress adress;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

    public Client(String s, String string, String email, String encode) {}


    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<ClientTable> getClientTables() {
        return clientTables;
    }

    public void setClientTables(List<ClientTable> clientTables) {
        this.clientTables = clientTables;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Long getId() {
        return id;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<ReviewClientProduct> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewClientProduct> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                '}';
    }

    public void addClientTable(ClientTable clientTable){
        this.clientTables.add(clientTable);
        clientTable.setClient(this);
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setClient(this);
    }

    public void addReview(ReviewClientProduct review) {
        this.reviews.add(review);
        review.setClient(this);
    }

    // Método para agregar una dirección
    public void addAdress(Adress adress) {
        this.adress = adress;
    }

}
