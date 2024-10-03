package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.SectorType;

import java.time.LocalDateTime;
import java.util.Set;

public record ClientTableDTO(Set<Long> clientIds, Long tableId, SectorType sectorType, LocalDateTime reservationStart) {}
