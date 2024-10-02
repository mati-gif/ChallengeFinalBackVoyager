package mindhub.VoyagerRestaurante.services;

import mindhub.VoyagerRestaurante.dtos.LoginDTO;
import mindhub.VoyagerRestaurante.dtos.RegisterDTO;
import mindhub.VoyagerRestaurante.dtos.ClientDTO;
import mindhub.VoyagerRestaurante.models.Client;

public interface AuthService {
    String login(LoginDTO loginDTO);
    Client register(RegisterDTO registerDTO);
    ClientDTO getCurrentClient(String email);
}
