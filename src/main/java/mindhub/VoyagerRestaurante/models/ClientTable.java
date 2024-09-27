package mindhub.VoyagerRestaurante.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ClientTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime initialDate;

    private LocalDateTime finalDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private List<Client> dinners;

    @ManyToOne(fetch = FetchType.EAGER)
    private Table table;

    public ClientTable() {
    }

    public ClientTable(long id, LocalDateTime initialDate, LocalDateTime finalDate) {
        this.id = id;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
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

    public List<Client> getDinners() {
        return dinners;
    }

    public void setDinners(List<Client> dinners) {
        this.dinners = dinners;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "ClientTable{" +
                "id=" + id +
                ", initialDate=" + initialDate +
                ", finalDate=" + finalDate +
                '}';
    }
}
