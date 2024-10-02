package mindhub.VoyagerRestaurante.repositories;

import mindhub.VoyagerRestaurante.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

