package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.Category;

public record ProductCreateDTO(String nameProduct, Double priceProduct, Category category,String details,String img) {
}
