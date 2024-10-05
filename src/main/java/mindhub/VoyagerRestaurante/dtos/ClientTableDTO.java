package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.ClientTable;
import mindhub.VoyagerRestaurante.models.SectorType;
import mindhub.VoyagerRestaurante.models.TableStatus;

import java.time.LocalDateTime;

public class ClientTableDTO {
    private Long id;
    private int tableNumber;
    private int seats;
    private SectorType sector;
    private TableStatus status;
    private Long tableId;  // Este es el ID de la mesa
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;

    public ClientTableDTO() {
    }

    // Constructor que acepta un ClientTable
    public ClientTableDTO(ClientTable clientTable) {
        this.id = clientTable.getTable().getId();
        this.tableNumber = clientTable.getTable().getTableNumber();
        this.seats = clientTable.getTable().getCapacity();
        this.sector = clientTable.getTable().getSectorType();
        this.status = clientTable.getTable().getState();
        this.reservationStart = clientTable.getInitialDate();
        this.reservationEnd = clientTable.getFinalDate();
    }

    // Constructor que acepta parámetros individuales (el que estás intentando usar)
    public ClientTableDTO(Long id, int tableNumber, int seats, SectorType sector, TableStatus status, LocalDateTime reservationStart, LocalDateTime reservationEnd) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.seats = seats;
        this.sector = sector;
        this.status = status;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
    }

    // Getters y setters
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

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public LocalDateTime getReservationStart() {
        return reservationStart;
    }

    public void setReservationStart(LocalDateTime reservationStart) {
        this.reservationStart = reservationStart;
    }

    public LocalDateTime getReservationEnd() {
        return reservationEnd;
    }

    public void setReservationEnd(LocalDateTime reservationEnd) {
        this.reservationEnd = reservationEnd;
    }
}
