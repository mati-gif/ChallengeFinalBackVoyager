package mindhub.VoyagerRestaurante.serviceSecurity;



import mindhub.VoyagerRestaurante.models.Client;
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
    private ClientService clientService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> client = clientService.findByEmail(username);
        if (client.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        if (client.get().getEmail().contains("admin")){
            return User
                    .withUsername(username)
                    .password(client.get().getPassword())
                    .roles("ADMIN")
                    .build();
        }
        return User
                .withUsername(username)
                .password(client.get().getPassword())
                .roles("CLIENT")
                .build();
    }
}