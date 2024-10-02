package mindhub.VoyagerRestaurante.services;

import mindhub.VoyagerRestaurante.models.ReviewClientProduct;

import java.util.List;

public interface ReviewClientProductService {
    ReviewClientProduct saveReview(ReviewClientProduct review);

    List<ReviewClientProduct> getReviewsByProduct(Long productId);

    List<ReviewClientProduct> getReviewsByClient(Long clientId);

    void deleteReview(Long id);
}
