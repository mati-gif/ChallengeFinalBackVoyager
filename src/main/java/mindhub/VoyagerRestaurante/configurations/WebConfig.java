package mindhub.VoyagerRestaurante.configurations;

import mindhub.VoyagerRestaurante.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

@Configuration
public class WebConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

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

                                // Rutas que requieren autenticación con el rol de CLIENT (para crear cosas específicas)
                                .requestMatchers(HttpMethod.POST,
                                        "/api/orders/create",  // Crear una orden
                                        "/api/clientTables/create",  // Crear una mesa de cliente
                                        "/api/products/purchase",  // Comprar productos
                                        "/api/address/create",  // Crear una dirección
                                        "/api/payments/pay-order",
                                        "api/orders/create/initiate-payment"// Pagar una orden (agregado)
                                ).permitAll()

                                // Rutas de lectura accesibles a CLIENT y ADMIN
                                .requestMatchers(HttpMethod.GET,
                                        "/api/products/", "/api/products/**", "/api/orders/", "/api/orders/**",
                                        "/api/tables/", "/ap/tables/**", "/api/clientTables/", "/api/clientTables/**",
                                        "/api/reviews/", "/api/reviews/**", "/api/auth/current",
                                        "/api/orders/ticket", "/api/address/", "/api/address/me", "/api/address/all"
                                ).permitAll()

                                // Rutas de escritura (creación, actualización, eliminación) solo accesibles a ADMIN
                                .requestMatchers(HttpMethod.POST, "/api/products/**", "/api/products/create","/api/orders/**", "/api/reviews/**","/api/clients", "/api/addres/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/products/**", "/api/orders/**", "/api/reviews/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/products/**", "/api/orders/**", "/api/reviews/**").permitAll()

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
