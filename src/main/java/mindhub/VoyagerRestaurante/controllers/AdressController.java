package mindhub.VoyagerRestaurante.controllers;

import mindhub.VoyagerRestaurante.dtos.AddressDTO;
import mindhub.VoyagerRestaurante.dtos.ClientAdressDTO;
import mindhub.VoyagerRestaurante.models.Adress;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.ClientAdress;
import mindhub.VoyagerRestaurante.models.TypeHome;
import mindhub.VoyagerRestaurante.repositories.AdressRepository;
import mindhub.VoyagerRestaurante.repositories.ClientAdressRepository;
import mindhub.VoyagerRestaurante.repositories.ClientRepository;
import mindhub.VoyagerRestaurante.serviceSecurity.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/address")
public class AdressController {

    @Autowired
    private ClientAdressRepository clientAdressRepository;

    @Autowired
    private AdressRepository adressRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JwtUtilService jwtUtilService;

    // Método para crear una dirección asociada al cliente logueado usando el token
    @PostMapping("/create")
    public ResponseEntity<?> createAddress(@RequestHeader("Authorization") String token, @RequestBody ClientAdressDTO clientAdressDTO) {
        // Extraer el token sin el prefijo "Bearer "
        String jwtToken = token.substring(7);

        // Obtener el email del cliente desde el token
        String email = jwtUtilService.extractUsername(jwtToken);

        // Buscar el cliente autenticado por su email
        Client client = clientRepository.findByEmail(email);

        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }

        Adress newAdress;

        // Validar que si el TypeHome es HOUSE, no se requiera el número de piso ni departamento
        if (clientAdressDTO.getTypeHome() == TypeHome.HOUSE) {
            // Validar que los campos de floorNumber y apartmentNumber no sean enviados para una casa
            if (clientAdressDTO.getFloorNumber() != null || clientAdressDTO.getAparmentNumber() != null) {
                return new ResponseEntity<>("Floor number and apartment number should not be provided for a HOUSE", HttpStatus.BAD_REQUEST);
            }

            newAdress = new Adress(
                    clientAdressDTO.getNameStreet(),
                    clientAdressDTO.getBetweenStreets(),
                    clientAdressDTO.getStreetNumber(),
                    clientAdressDTO.getTypeHome(),
                    clientAdressDTO.getZipCode()
            );

        } else if (clientAdressDTO.getTypeHome() == TypeHome.APARTMENT) {
            // Validar que los campos de piso y departamento estén presentes para APARTMENT
            if (clientAdressDTO.getFloorNumber() == null || clientAdressDTO.getAparmentNumber() == null) {
                return new ResponseEntity<>("Floor number and apartment number are required for an APARTMENT", HttpStatus.BAD_REQUEST);
            }

            newAdress = new Adress(
                    clientAdressDTO.getNameStreet(),
                    clientAdressDTO.getBetweenStreets(),
                    clientAdressDTO.getStreetNumber(),
                    clientAdressDTO.getTypeHome(),
                    clientAdressDTO.getFloorNumber(),
                    clientAdressDTO.getAparmentNumber(),
                    clientAdressDTO.getZipCode()
            );
        } else {
            return new ResponseEntity<>("Invalid TypeHome", HttpStatus.BAD_REQUEST);
        }

        // Guardar la dirección primero
        adressRepository.save(newAdress);

        // Crear la relación entre cliente y dirección
        ClientAdress clientAdress = new ClientAdress();
        clientAdress.setAdress(newAdress);
        clientAdress.setClient(client);

        // Guardar la relación
        clientAdressRepository.save(clientAdress);

        // Crear y devolver el DTO de la nueva dirección
        ClientAdressDTO createdAdressDTO = new ClientAdressDTO(clientAdress);
        return new ResponseEntity<>(createdAdressDTO, HttpStatus.CREATED);
    }


    // Método para obtener todas las direcciones
    @GetMapping("/all")
    public List<ClientAdressDTO> getAllAddresses() {
        List<ClientAdress> addresses = clientAdressRepository.findAll();
        return addresses.stream()
                .map(ClientAdressDTO::new) // Cambiado para mapear correctamente ClientAdress a ClientAdressDTO
                .collect(Collectors.toList());
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
        List<ClientAdress> clientAdresses = clientAdressRepository.findByClient(client);

        if (clientAdresses.isEmpty()) {
            return new ResponseEntity<>("No addresses found for this client", HttpStatus.NOT_FOUND);
        }

        List<ClientAdressDTO> clientAdressDTOS = clientAdresses.stream()
                .map(ClientAdressDTO::new) // Cambiado para mapear correctamente ClientAdress a ClientAdressDTO
                .collect(Collectors.toList());

        return new ResponseEntity<>(clientAdressDTOS, HttpStatus.OK);
    }
}
