package mindhub.VoyagerRestaurante.controllers;

import mindhub.VoyagerRestaurante.dtos.LoginDTO;
import mindhub.VoyagerRestaurante.dtos.RegisterDTO;
import mindhub.VoyagerRestaurante.dtos.ClientDTO;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.repositories.ClientRepository;
import mindhub.VoyagerRestaurante.serviceSecurity.JwtUtilService;
import mindhub.VoyagerRestaurante.services.AuthService;
import mindhub.VoyagerRestaurante.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private AuthService authService;
    @Autowired
    private ClientService clientService;

    // Endpoint para iniciar sesión y generar un token JWT.
    // AuthController.java
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            String token = authService.loginAndGenerateToken(loginDTO);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            // Asegúrate de devolver un mensaje con la respuesta de error
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        try {
            Client client = authService.register(registerDTO);
            return ResponseEntity.ok("Registration successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // El objeto Authentication proporciona información sobre el usuario actualmente autenticado, como su nombre de usuario, roles, y otros atributos.
//    @GetMapping("/current")
//    public ResponseEntity<?> getClient(Authentication authentication) {
//        // Retorna los detalles del cliente en la respuesta.
//        return ResponseEntity.ok(new ClientDTO(clientRepository.findByEmail(authentication.getName())));
//    }


    @GetMapping("/current")//metodo para obtener el usuario logueado(es decir autenticado).
    public ResponseEntity<?> getClient(Authentication authentication){

        Client client = clientService.findByEmail(authentication.getName());
        return new ResponseEntity<>(clientService.getClientDto(client),HttpStatus.OK);

    }
}