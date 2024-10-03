package mindhub.VoyagerRestaurante.repositories;

import mindhub.VoyagerRestaurante.models.ClientAdress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientAdressRepository extends JpaRepository<ClientAdress, Long> {

}
