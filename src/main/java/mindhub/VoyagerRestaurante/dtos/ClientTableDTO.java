package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.SectorType;
import mindhub.VoyagerRestaurante.models.Table;

import java.util.Set;

public record ClientTableDTO ( Set<Client> comensales, Table table, SectorType sectorType ) {}
