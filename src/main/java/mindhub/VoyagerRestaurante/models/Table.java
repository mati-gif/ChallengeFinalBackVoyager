package mindhub.VoyagerRestaurante.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@jakarta.persistence.Table(name = "\"table\"")  // Usamos comillas dobles para escapar la palabra reservada
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean state;
    private int capacity;

    @Enumerated
    private SectorType sectorType;

    @OneToMany(mappedBy = "table", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ClientTable> clientTables = new ArrayList<>();

    public Table() {}

    public Table(boolean state, int capacity, SectorType sectorType) {
        this.state = state;
        this.capacity = capacity;
        this.sectorType = sectorType;
    }

    public long getId() {
        return id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
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
