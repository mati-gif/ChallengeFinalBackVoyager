package mindhub.VoyagerRestaurante.controllers;

import mindhub.VoyagerRestaurante.dtos.AddressDTO;
import mindhub.VoyagerRestaurante.models.Adress;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.TypeHome;
import mindhub.VoyagerRestaurante.repositories.AdressRepository;
import mindhub.VoyagerRestaurante.repositories.ClientRepository;
import mindhub.VoyagerRestaurante.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/addresses")
public class AdressController {

    @Autowired
    private AdressRepository adressRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    // Método para crear una dirección
    @PostMapping("/create")
    public ResponseEntity<?> createAddress(@RequestBody AddressDTO addressDTO, Authentication authentication) {
        // Obtener el cliente autenticado
        Client client = clientRepository.findByEmail(authentication.getName());

        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }

        // Validar que si el TypeHome es HOUSE, no se requiera el número de piso ni departamento
        if (addressDTO.getTypeHome() == TypeHome.HOUSE) {
            addressDTO = new AddressDTO(new Adress(
                    addressDTO.getNameStreet(),
                    addressDTO.getBetweenStreets(),
                    addressDTO.getStreetNumber(),
                    addressDTO.getTypeHome(),
                    addressDTO.getZipCode()
            ));
        } else {
            addressDTO = new AddressDTO(new Adress(
                    addressDTO.getNameStreet(),
                    addressDTO.getBetweenStreets(),
                    addressDTO.getStreetNumber(),
                    addressDTO.getTypeHome(),
                    addressDTO.getFloorNumber(),
                    addressDTO.getAparmentNumber(),
                    addressDTO.getZipCode()
            ));
        }

        // Crear la dirección y asignarla al cliente autenticado
        Adress newAdress = new Adress(
                addressDTO.getNameStreet(),
                addressDTO.getBetweenStreets(),
                addressDTO.getStreetNumber(),
                addressDTO.getTypeHome(),
                addressDTO.getZipCode()
        );

        newAdress.setClient(client);
        adressRepository.save(newAdress);

        return new ResponseEntity<>("Address created successfully", HttpStatus.CREATED);
    }

    // Método para obtener todas las direcciones
    @GetMapping("/all")
    public List<AddressDTO> getAllAddresses() {
        List<Adress> addresses = adressRepository.findAll();
        return addresses.stream().map(AddressDTO::new).collect(Collectors.toList());
    }

    // Método para obtener la dirección del cliente autenticado
    @GetMapping("/me")
    public ResponseEntity<?> getMyAddress(Authentication authentication) {
        // Obtener el cliente autenticado
        Client client = clientRepository.findByEmail(authentication.getName());

        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }

        // Obtener la dirección del cliente
        List<Adress> addresses = adressRepository.findByClient(client);

        if (addresses.isEmpty()) {
            return new ResponseEntity<>("No addresses found for this client", HttpStatus.NOT_FOUND);
        }

        List<AddressDTO> addressDTOs = addresses.stream()
                .map(AddressDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(addressDTOs, HttpStatus.OK);
    }
}
