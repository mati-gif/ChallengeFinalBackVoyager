package mindhub.VoyagerRestaurante.repositories;

import mindhub.VoyagerRestaurante.models.ReviewClientProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewClientProductRepository extends JpaRepository<ReviewClientProduct, Long> {
    List<ReviewClientProduct> findByProductId(Long productId);
    List<ReviewClientProduct> findByClientId(Long clientId);
}
