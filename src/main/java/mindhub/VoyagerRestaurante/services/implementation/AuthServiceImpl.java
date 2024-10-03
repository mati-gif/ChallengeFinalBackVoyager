package mindhub.VoyagerRestaurante.services.implementation;

import mindhub.VoyagerRestaurante.dtos.ClientDTO;
import mindhub.VoyagerRestaurante.dtos.LoginDTO;
import mindhub.VoyagerRestaurante.dtos.RegisterDTO;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.repositories.ClientRepository;
import mindhub.VoyagerRestaurante.serviceSecurity.JwtUtilService;
import mindhub.VoyagerRestaurante.services.AuthService;
import mindhub.VoyagerRestaurante.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
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

    // Ajustes en los patrones de validación
    private final Pattern namePattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ][A-Za-zÁÉÍÓÚáéíóúñÑ ]*$"); // No permite que empiece con espacio
    private final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private final Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\p{Punct}])[A-Za-z\\d\\p{Punct}]{8,}$");

//    @Override
//    public String login(LoginDTO loginDTO) {
//        // Validaciones de email y contraseña
//        validateEmail(loginDTO.email());
//        validatePassword(loginDTO.password());
//
//        // Verificar si el cliente existe
//        Optional<Client> existingClient = clientService.findByEmail(loginDTO.email());
//        if (existingClient.isEmpty()) {
//            throw new IllegalArgumentException("Email not registered");
//        }
//
//        // Intentar autenticación
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Incorrect password"); // Mensaje específico para contraseña incorrecta
//        }
//
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.email());
//        return JwtUtilService.generateToken(userDetails);
//    }
//
//    @Override
//    public Client register(RegisterDTO registerDTO) {
//        // Validar todos los campos
//        validateName(registerDTO.firstName(), "First name");
//        validateName(registerDTO.lastName(), "Last name");
//        validateEmail(registerDTO.email());
//        validatePassword(registerDTO.password());
//
//        // Verificar si el cliente ya existe
//        Optional<Client> existingClient = clientService.findByEmail(registerDTO.email());
//        if (existingClient.isPresent()) {
//            throw new IllegalArgumentException("Email already in use");
//        }
//
//        // Crear nuevo cliente y asignar los datos correctamente
//        Client client = new Client(
//                registerDTO.firstName(),
//                registerDTO.lastName(),
//                registerDTO.email(),
//                passwordEncoder.encode(registerDTO.password()),
//                registerDTO.phoneNumbers() // Ahora los números de teléfono son List<String>
//        );
//
//        // Guardar el nuevo cliente en la base de datos
//        clientRepository.save(client);
//
//        return client;
//    }
//
//
//
//    @Override
//    public ClientDTO getCurrentClient(String email) {
//        Optional<Client> client = clientService.findByEmail(email);
//        if (client.isEmpty()) {
//            throw new IllegalArgumentException("Client with email " + email + " not found");
//        }
//        return new ClientDTO(client.orElse(null));
//    }
//
//    // Métodos de validación
//    private void validateName(String name, String fieldName) {
//        if (name == null || name.trim().isBlank()) {
//            throw new IllegalArgumentException(fieldName + " cannot be empty or start with a space");
//        }
//        if (!namePattern.matcher(name).matches()) {
//            throw new IllegalArgumentException(fieldName + " is not valid, only letters are allowed");
//        }
//    }
//
//    private void validateEmail(String email) {
//        if (email == null || email.trim().isBlank()) {
//            throw new IllegalArgumentException("Email cannot be empty or start with a space");
//        }
//        if (!emailPattern.matcher(email).matches()) {
//            throw new IllegalArgumentException("Invalid email format, must contain @ and .com");
//        }
//    }
//
//    private void validatePassword(String password) {
//        if (!passwordPattern.matcher(password).matches()) {
//            throw new IllegalArgumentException("Password must be at least 8 characters long, contain one uppercase letter, one lowercase letter, one number, and one special character.");
//        }
//    }







    //------------------------------------------------------------------------------------
    // Método principal que gestiona todo el flujo de creación de cliente y cuenta.
    @Override
    public Client register(RegisterDTO registerDTO) {

        // 1. Validar los datos del DTO de registro
        validateRegisterDto(registerDTO);
        //2.Verificar si el email ya existe en la base de datos.
        validateEmail(registerDTO);
        // 3. Crear y guardar un nuevo cliente
        Client client = saveClient(createClient(registerDTO));
        // 4. Crear y guardar una nueva cuenta para el cliente

        return client;

    }
//------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------
//    Metodo que valida los campos FirstName, LastName, Email y Password usando el record RegisterDto.
    @Override
    public void validateRegisterDto(RegisterDTO registerDTO) {
        if (registerDTO.firstName().isBlank() || registerDTO.firstName().isEmpty() ) {
            throw new IllegalArgumentException("The name field must not be empty");
        }
        if (registerDTO.lastName().isBlank()  ) {
            throw new IllegalArgumentException("The last name field must not be empty");
        }
        if (registerDTO.email().isBlank()  || registerDTO.email().isEmpty() || !registerDTO.email().contains("@") || registerDTO.email().trim().isEmpty()) {
            throw new IllegalArgumentException("The email field must not be empty");
        }
        if (registerDTO.password().isBlank()  || registerDTO.password().isEmpty() || registerDTO.password().trim().isEmpty()) {
            throw new IllegalArgumentException("The password field must not be empty");
        }
        if (registerDTO.password().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
    }

    //Metodo que valida si ya hay un email en la base de datos.
    @Override
    public void validateEmail(RegisterDTO registerDTO) {
        if (clientService.findByEmail(registerDTO.email()) != null) {
            throw new IllegalArgumentException("Email is already in use");
        }
    }
//-------------------------------------------------------------------------------

    //-------------------------------------------------------------------------------
//metodo para crear un nuevo cliente.
    @Override
    public Client createClient(RegisterDTO  registerDTO) {
        Client client = new Client(
                registerDTO.firstName(),
                registerDTO.lastName(),
                registerDTO.email(),
                passwordEncoder.encode(registerDTO.password()),
                registerDTO.phoneNumbers());




        return client;
    }
    //-------------------------------------------------------------------------------
//---------------------------------------------------------------------------------
//metodo que usa el metodo save() para guardar en la base de datos un cliente.
    @Override
    public Client saveClient(Client client) {
        return clientService.saveClient(client);
    }
//---------------------------------------------------------------------------------


    //-------------------------------------------------------------------------------
// Método principal que autentica al usuario y genera el JWT.
    @Override
    public String loginAndGenerateToken(LoginDTO loginDTO) {
        authenticateUser(loginDTO);
        return generateJwtForUser(loginDTO.email());
    }
    //-------------------------------------------------------------------------------
// Autenticar al usuario
    @Override
    public void authenticateUser(LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password())
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Email or Password invalid");
        }
    }
    //-------------------------------------------------------------------------------
// Generar el JWT para el usuario autenticado
    @Override
    public String generateJwtForUser(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return jwtUtilService.generateToken(userDetails);
    }
}