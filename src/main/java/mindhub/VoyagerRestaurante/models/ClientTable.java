package mindhub.VoyagerRestaurante.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
public class ClientTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime initialDate;
    private LocalDateTime finalDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    @JsonManagedReference
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_id")
    @JsonManagedReference
    private Table table;

    public ClientTable() {
    }

    public ClientTable(LocalDateTime now, LocalDateTime localDateTime) {}

    public ClientTable(LocalDateTime initialDate, LocalDateTime finalDate, Client client, Table table) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.client = client;
        this.table = table;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDateTime getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDateTime finalDate) {
        this.finalDate = finalDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
