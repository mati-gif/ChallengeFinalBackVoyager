package mindhub.VoyagerRestaurante.repositories;

import mindhub.VoyagerRestaurante.models.Adress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdressRepository extends JpaRepository<Adress, Long> {
    Optional<Adress> findById(Long id);
}
