package mindhub.VoyagerRestaurante.services;

import mindhub.VoyagerRestaurante.dtos.LoginDTO;
import mindhub.VoyagerRestaurante.dtos.RegisterDTO;
import mindhub.VoyagerRestaurante.dtos.ClientDTO;
import mindhub.VoyagerRestaurante.models.Client;

public interface AuthService {
    Client register(RegisterDTO registerDTO);
    void validateRegisterDto(RegisterDTO registerDTO);
    void validateEmail(RegisterDTO registerDTO);
    Client createClient(RegisterDTO registerDTO);
    Client saveClient(Client client);

//    Boolean existsByNumber(String number);
//    String generateUniqueAccountNumber();

    String generateJwtForUser(String email);
    void authenticateUser(LoginDTO loginDTO);
    String loginAndGenerateToken(LoginDTO loginDTO);
}
