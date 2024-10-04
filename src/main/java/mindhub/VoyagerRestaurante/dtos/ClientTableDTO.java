package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.SectorType;
import mindhub.VoyagerRestaurante.models.TableStatus;

import java.time.LocalDateTime;

public record ClientTableDTO(
        Long tableId,
        int tableNumber,
        int capacity,
        SectorType sector,
        TableStatus state,
        LocalDateTime reservationStart,
        LocalDateTime reservationEnd) {
}
