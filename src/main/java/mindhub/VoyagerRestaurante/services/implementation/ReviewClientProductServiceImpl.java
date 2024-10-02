package mindhub.VoyagerRestaurante.services.implementation;

import mindhub.VoyagerRestaurante.models.ReviewClientProduct;
import mindhub.VoyagerRestaurante.repositories.ReviewClientProductRepository;
import mindhub.VoyagerRestaurante.services.ReviewClientProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewClientProductServiceImpl implements ReviewClientProductService {

    @Autowired
    private ReviewClientProductRepository reviewRepository;

    @Override
    public ReviewClientProduct saveReview(ReviewClientProduct review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<ReviewClientProduct> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public List<ReviewClientProduct> getReviewsByClient(Long clientId) {
        return reviewRepository.findByClientId(clientId);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}

