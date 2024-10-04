package mindhub.VoyagerRestaurante.services.implementation;

import mindhub.VoyagerRestaurante.dtos.LoginDTO;
import mindhub.VoyagerRestaurante.dtos.RegisterDTO;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.repositories.ClientRepository;
import mindhub.VoyagerRestaurante.serviceSecurity.JwtUtilService;
import mindhub.VoyagerRestaurante.services.AuthService;
import mindhub.VoyagerRestaurante.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {

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
    private ClientService clientService;

    // Patrones para validaciones
    private final Pattern namePattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ][A-Za-zÁÉÍÓÚáéíóúñÑ ]*$"); // Nombres solo con letras
    private final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private final Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\p{Punct})[A-Za-z\\d\\p{Punct}]{8,}$");

    // Método para registrar un cliente
    @Override
    public Client register(RegisterDTO registerDTO) {
        // Validar todos los campos del DTO
        validateRegisterDto(registerDTO);

        // Verificar si el email ya existe en la base de datos
        Client existingClient = clientRepository.findByEmail(registerDTO.email());
        if (existingClient != null) {
            throw new IllegalArgumentException("Email is already registered");
        }

        // Crear y guardar un nuevo cliente
        Client client = createClient(registerDTO);
        return saveClient(client);
    }

    // Validar los campos del DTO de registro
    @Override
    public void validateRegisterDto(RegisterDTO registerDTO) {
        validateName(registerDTO.firstName(), "First name");
        validateName(registerDTO.lastName(), "Last name");
        validateEmail(registerDTO.email());
        validatePassword(registerDTO.password());
        validatePhoneNumber(registerDTO.phoneNumbers());
    }

    // Validar nombre (sin números ni caracteres especiales)
    private void validateName(String name, String fieldName) {
        if (name == null || name.trim().isBlank()) {
            throw new IllegalArgumentException(fieldName + " can not be empty or start with a space");
        }
        if (name.length() < 2) {
            throw new IllegalArgumentException(fieldName + " too short. Enter at least 2 characters");
        }
        if (!namePattern.matcher(name).matches()) {
            throw new IllegalArgumentException(fieldName + " is not valid, only letters are allowed");
        }
    }

    // Validar el email
    private void validateEmail(String email) {
        if (email == null || email.trim().isBlank()) {
            throw new IllegalArgumentException("Email can not be empty or contain only spaces");
        }
        if (!email.contains("@")) {  throw new IllegalArgumentException("Invalid email. It must contain an '@' character."); }
        if (!email.contains(".com") && !email.contains(".net") && !email.contains(".org") &&
                !email.contains(".co") && !email.contains(".info")) { throw new IllegalArgumentException("Invalid email. Please enter a valid domain extension since '.com', '.net', '.org', '.co' or '.info'.");}
        if (email.contains("@.")) {  throw new IllegalArgumentException("Invalid email. Please provide a valid domain since 'gmail', 'yahoo', etc., " +
                "between the characters '@' and the character '.'"); }
//        if (!emailPattern.matcher(email).matches()) {
//            throw new IllegalArgumentException("Invalid email format, must contain @ and a valid domain");
//        }
    }

    // Validar contraseña
    private void validatePassword(String password) {
        if (password == null || password.trim().isBlank()) {
            throw new IllegalArgumentException("Password can not be empty or contain only spaces");
        }
        if (!passwordPattern.matcher(password).matches()) {
            throw new IllegalArgumentException("Password must contain at least 8 characters, including uppercase, lowercase, number, and special character");
        }
    }

    // Validar número de teléfono (mínimo un número y sin espacios en blanco)
    private void validatePhoneNumber(List<String> phoneNumbers) {
        if (phoneNumbers == null || phoneNumbers.isEmpty()) {
            throw new IllegalArgumentException("At least one phone number is required");
        }
        for (String phoneNumber : phoneNumbers) {
            if (phoneNumber.trim().isBlank()) {
                throw new IllegalArgumentException("Phone number cannot contain only spaces or be empty");
            }
        }
    }

    // Método para autenticar y generar token
    @Override
    public String login(LoginDTO loginDTO) {
        // Validar email y contraseña en login
        validateEmail(loginDTO.email());
        validatePassword(loginDTO.password());

        // Verificar si el email existe en la base de datos y obtener el cliente
        Client client = clientRepository.findByEmail(loginDTO.email());
        if (client == null) {
            throw new IllegalArgumentException("Email not registered");
        }

        // Verificar si la contraseña es correcta
        if (!passwordEncoder.matches(loginDTO.password(), client.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        // Si todo es correcto, generar el JWT
        return generateJwtForUser(loginDTO.email());
    }

    // Generar el JWT para el usuario autenticado
    @Override
    public String generateJwtForUser(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return jwtUtilService.generateToken(userDetails);
    }

    // Crear cliente
    @Override
    public Client createClient(RegisterDTO registerDTO) {
        return new Client(
                registerDTO.firstName(),
                registerDTO.lastName(),
                registerDTO.email(),
                passwordEncoder.encode(registerDTO.password()),
                registerDTO.phoneNumbers()
        );
    }

    // Guardar cliente en la base de datos
    @Override
    public Client saveClient(Client client) {
        return clientService.saveClient(client);
    }

    @Override
    public void authenticateUser(LoginDTO loginDTO) {
        // Implementación pendiente si es necesario
    }
}