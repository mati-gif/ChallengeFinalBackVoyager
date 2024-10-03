package mindhub.VoyagerRestaurante.services;

import mindhub.VoyagerRestaurante.dtos.LoginDTO;
import mindhub.VoyagerRestaurante.dtos.RegisterDTO;
import mindhub.VoyagerRestaurante.models.Client;

public interface AuthService {
    Client register(RegisterDTO registerDTO);
    Client createClient(RegisterDTO registerDTO);
    Client saveClient(Client client);

    // Autenticar al usuario
    void authenticateUser(LoginDTO loginDTO);

    String generateJwtForUser(String email);

    // Validar los campos del DTO de registro
    void validateRegisterDto(RegisterDTO registerDTO);

    String login(LoginDTO loginDTO);
}
