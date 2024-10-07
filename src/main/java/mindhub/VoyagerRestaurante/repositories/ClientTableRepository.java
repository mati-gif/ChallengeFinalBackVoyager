package mindhub.VoyagerRestaurante.repositories;

import mindhub.VoyagerRestaurante.models.ClientTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientTableRepository extends JpaRepository<ClientTable, Long> {
}
