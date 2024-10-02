package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.Product;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO (LocalDateTime hourOrden, List<Product> products, Client client){
}
