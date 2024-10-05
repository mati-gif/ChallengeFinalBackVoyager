package mindhub.VoyagerRestaurante.repositories;

import mindhub.VoyagerRestaurante.models.Adress;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.ClientAdress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientAdressRepository extends JpaRepository<ClientAdress, Long> {
    List<ClientAdress> findByClient(Client client);
}
