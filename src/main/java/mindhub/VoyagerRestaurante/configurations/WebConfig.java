package mindhub.VoyagerRestaurante.configurations;

import mindhub.VoyagerRestaurante.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration  // Agregamos la anotación para marcar esta clase como de configuración
public class WebConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter; // Filtro personalizado para manejar la autenticación JWT.

    @Autowired
    private CorsConfigurationSource corsConfigurationSource; // Fuente de configuración para CORS (Cross-Origin Resource Sharing).

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(authorize ->
                        authorize
                                // Rutas públicas permitidas
                                .requestMatchers("/api/auth/login", "/api/auth/register", "/h2-console/**").permitAll()
                                // Rutas que requieren autenticación con el rol de CLIENT
                                .requestMatchers("/api/auth/current", "/api/orders/create", "/api/reviews/create", "/api/tables/create", "/api/products/create", "/api/clientTables/create").hasRole("CLIENT")
                                // Rutas que requieren autenticación con el rol de ADMIN
                                .requestMatchers("/api/clients/**", "/api/tables/**", "/api/reviews/**", "/api/products/**", "/api/orders/**").hasRole("ADMIN")
                                // Cualquier otra ruta requiere autenticación
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
