package mindhub.VoyagerRestaurante.repositories;

import mindhub.VoyagerRestaurante.models.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {
}