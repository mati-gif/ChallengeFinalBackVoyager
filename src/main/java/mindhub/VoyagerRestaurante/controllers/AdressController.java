package mindhub.VoyagerRestaurante.controllers;

import mindhub.VoyagerRestaurante.dtos.AddressDTO;
import mindhub.VoyagerRestaurante.models.Adress;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.TypeHome;
import mindhub.VoyagerRestaurante.repositories.AdressRepository;
import mindhub.VoyagerRestaurante.repositories.ClientRepository;
import mindhub.VoyagerRestaurante.serviceSecurity.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/address")
public class AdressController {

    @Autowired
    private AdressRepository adressRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JwtUtilService jwtUtilService;

    // Método para crear una dirección asociada al cliente logueado usando el token
    @PostMapping("/create")
    public ResponseEntity<?> createAddress(@RequestHeader("Authorization") String token, @RequestBody AddressDTO addressDTO) {
        // Extraer el token sin el prefijo "Bearer "
        String jwtToken = token.substring(7);

        // Obtener el email del cliente desde el token
        String email = jwtUtilService.extractUsername(jwtToken);

        // Buscar el cliente autenticado por su email
        Client client = clientRepository.findByEmail(email);

        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }

        // Validar que si el TypeHome es HOUSE, no se requiera el número de piso ni departamento
        Adress newAdress;

        if (addressDTO.getTypeHome() == TypeHome.HOUSE) {
            // Validar que los campos de floorNumber y apartmentNumber no sean enviados para una casa
            if (addressDTO.getFloorNumber() != null || addressDTO.getAparmentNumber() != null) {
                return new ResponseEntity<>("Floor number and apartment number should not be provided for a HOUSE", HttpStatus.BAD_REQUEST);
            }

            newAdress = new Adress(
                    addressDTO.getNameStreet(),
                    addressDTO.getBetweenStreets(),
                    addressDTO.getStreetNumber(),
                    addressDTO.getTypeHome(),
                    addressDTO.getZipCode()
            );

        } else if (addressDTO.getTypeHome() == TypeHome.APARTMENT) {
            // Validar que los campos de piso y departamento estén presentes para APARTMENT
            if (addressDTO.getFloorNumber() == null || addressDTO.getAparmentNumber() == null) {
                return new ResponseEntity<>("Floor number and apartment number are required for an APARTMENT", HttpStatus.BAD_REQUEST);
            }

            newAdress = new Adress(
                    addressDTO.getNameStreet(),
                    addressDTO.getBetweenStreets(),
                    addressDTO.getStreetNumber(),
                    addressDTO.getTypeHome(),
                    addressDTO.getFloorNumber(),
                    addressDTO.getAparmentNumber(),
                    addressDTO.getZipCode()
            );

        } else {
            return new ResponseEntity<>("Invalid TypeHome", HttpStatus.BAD_REQUEST);
        }

        // Asignar la dirección al cliente autenticado
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
    public ResponseEntity<?> getMyAddress(@RequestHeader("Authorization") String token) {
        // Extraer el token sin el prefijo "Bearer "
        String jwtToken = token.substring(7);

        // Obtener el email del cliente desde el token
        String email = jwtUtilService.extractUsername(jwtToken);

        // Buscar el cliente autenticado por su email
        Client client = clientRepository.findByEmail(email);

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
