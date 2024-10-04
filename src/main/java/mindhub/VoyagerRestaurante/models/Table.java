package mindhub.VoyagerRestaurante.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@jakarta.persistence.Table(name = "tables")  // Usamos comillas dobles para escapar la palabra reservada
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int tableNumber;

    @Enumerated(EnumType.STRING)
    private TableStatus state;
    private int capacity;

    @Enumerated(EnumType.STRING)
    private SectorType sectorType;

    @OneToMany(mappedBy = "table", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ClientTable> clientTables = new ArrayList<>();//LISTA DE RESERVAS

    public Table() {}

    public Table(int capacity, SectorType sectorType, int tableNumber, TableStatus state) {
        this.capacity = capacity;
        this.sectorType = sectorType;
        this.tableNumber = tableNumber;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public TableStatus getState() {
        return state;
    }

    public void setState(TableStatus state) {
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

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
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
