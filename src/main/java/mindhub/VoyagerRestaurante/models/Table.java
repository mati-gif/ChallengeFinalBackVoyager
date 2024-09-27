package mindhub.VoyagerRestaurante.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean state;

    private int capacity;

    private SectorType sectorType;

    @OneToMany(mappedBy = "table", fetch = FetchType.EAGER)
    private List<ClientTable> clientTables;

    public Table() {
    }

    public Table(long id, boolean state, int capacity, SectorType sectorType) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", state=" + state +
                ", capacity=" + capacity +
                ", sectorType=" + sectorType +
                '}';
    }

    public void addClientTable(ClientTable clientTable){
        this.clientTables.add(clientTable);
        clientTable.setTable(this);
    }
}
