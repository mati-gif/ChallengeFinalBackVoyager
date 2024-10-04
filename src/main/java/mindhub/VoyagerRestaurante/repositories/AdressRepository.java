package mindhub.VoyagerRestaurante.repositories;

import mindhub.VoyagerRestaurante.models.Adress;
import mindhub.VoyagerRestaurante.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdressRepository extends JpaRepository<Adress, Long> {

}
