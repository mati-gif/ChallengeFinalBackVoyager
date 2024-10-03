package mindhub.VoyagerRestaurante.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"table\"")  // Use double quotes to escape reserved words
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean isAvailable; // Renamed for clarity
    private int capacity;

    @Enumerated(EnumType.STRING) // Specify that the enum should be stored as a string
    private SectorType sectorType;

    @OneToMany(mappedBy = "table", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ClientTable> clientTables = new ArrayList<>(); // Renamed to 'clientTables'

    public Table() {}

    public Table(boolean isAvailable, int capacity, SectorType sectorType) {
        this.isAvailable = isAvailable;
        this.capacity = capacity;
        this.sectorType = sectorType;
    }

    public long getId() {
        return id;
    }

    public boolean isAvailable() {
        return isAvailable; // Updated getter
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable; // Updated setter
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public SectorType getSectorType() {
        return sectorType;
    }

    public void setSectorType(SectorType sectorType) {
        this.sectorType = sectorType;
    }

    public List<ClientTable> getClientTables() {
        return clientTables;
    }

    public void setClientTables(List<ClientTable> clientTables) {
        this.clientTables = clientTables;
    }

    public void addClientTable(ClientTable clientTable){
        this.clientTables.add(clientTable);
        clientTable.setTable(this);
    }
}
