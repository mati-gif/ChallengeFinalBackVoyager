package mindhub.VoyagerRestaurante.controllers;

import mindhub.VoyagerRestaurante.dtos.LoginDTO;
import mindhub.VoyagerRestaurante.dtos.RegisterDTO;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.services.AuthService;
import mindhub.VoyagerRestaurante.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ClientService clientService;

    // Endpoint para iniciar sesión y generar un token JWT.
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            // Intentar login y generar token
            String token = authService.login(loginDTO);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            // Devolver mensaje de error en caso de fallas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // Endpoint para registrar un nuevo cliente
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        try {
            // Intentar registro
            Client client = authService.register(registerDTO);
            return ResponseEntity.ok("Registration successful");
        } catch (IllegalArgumentException e) {
            // Devolver mensaje de error en caso de validación fallida
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/current")//metodo para obtener el usuario logueado(es decir autenticado).
    public ResponseEntity<?> getClient(Authentication authentication){

        Client client = clientService.findByEmail(authentication.getName());
        return new ResponseEntity<>(clientService.getClientDto(client),HttpStatus.OK);

    }
}


