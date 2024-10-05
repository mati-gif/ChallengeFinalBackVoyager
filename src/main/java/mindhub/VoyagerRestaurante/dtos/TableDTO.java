package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.SectorType;
import mindhub.VoyagerRestaurante.models.Table;
import mindhub.VoyagerRestaurante.models.TableStatus;

public class TableDTO {
    private Long id;
    private int tableNumber;
    private int seats;
    private SectorType sector;
    private TableStatus status;

    public TableDTO(Table table) {
        this.id = table.getId();
        this.tableNumber = table.getTableNumber();
        this.sector = table.getSectorType();
        this.seats = table.getCapacity();
        this.status = table.getState();
    }

    public Long getId() {
        return id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getSeats() {
        return seats;
    }

    public SectorType getSector() {
        return sector;
    }

    public TableStatus getStatus() {
        return status;
    }
}
