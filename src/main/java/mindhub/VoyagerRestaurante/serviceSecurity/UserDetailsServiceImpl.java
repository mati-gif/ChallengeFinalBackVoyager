package mindhub.VoyagerRestaurante.serviceSecurity;



import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.repositories.ClientRepository;
import mindhub.VoyagerRestaurante.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(username);
        if(client == null){
            throw new UsernameNotFoundException(username);
        }

        if(client.getEmail().contains("admin")){

            return User
                    .withUsername(username)
                    .password(client.getPassword())
                    .roles("ADMIN")
                    .build();

        }


        return User //retorna un UserDetails que es el usuario que queremos tener en el contextHolder.
                .withUsername(username)  // Crea un objeto UserDetails con el nombre de usuario proporcionado.
                .password(client.getPassword())  // Establece la contraseña del usuario (normalmente esta estaría cifrada).
                .roles("CLIENT")  //Asigna un rol al usuario. Aquí se asigna el rol CLIENT.
                .build(); //Construye y retorna la instancia de UserDetails creada.
        //una vez que lo tenemos ahi queremos crearle un token.
    }
}