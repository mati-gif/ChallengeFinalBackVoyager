package mindhub.VoyagerRestaurante.dtos;

import mindhub.VoyagerRestaurante.models.Category;

public record ModifyAllProductDTO(Long id, String nameProduct, Double priceProduct, Category category, String details, String img) {
}
