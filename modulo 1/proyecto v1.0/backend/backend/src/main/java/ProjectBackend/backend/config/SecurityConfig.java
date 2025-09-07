package ProjectBackend.backend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity //Clase para configuraciones de springbootSecurity (configurar la seguridad de mi aplicación
public class SecurityConfig {


    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //.csrf(csrf -> csrf.disable()) //Cross-Site Request Forgery que significa Falsificación de solicitud entre sitios
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("v1/index2").permitAll() //dejamos las url que no necesitan autorización
                                .anyRequest().authenticated()             //el resto requiere autenticación
                )
                .formLogin(form ->
                        form
                                .successHandler(mySuccessHandler()) // manejador de login exitoso
                                .permitAll() //login por formulario
                )
                .sessionManagement(session ->
                        session

                            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                            .invalidSessionUrl("/login") // si se crea una sesion erropnea ¿a donde se dirige?
                            .sessionFixation(sessionFix -> sessionFix.migrateSession()) //protección de ataque de fijación de sesión
                )
                .sessionManagement(session ->
                        session

                            .maximumSessions(1) // máximo de sesiones abiertas a la vez
                            .expiredUrl("/login") // si se expira la sesión a donde se redirige
                            .sessionRegistry(sessionRegistry()) //rastreamos los datos de la sesión del usuario
                )
                .logout(logout -> logout.permitAll())
                .build();
    }


    // Ejemplo de bean de AuthenticationSuccessHandler
    @Bean
    public AuthenticationSuccessHandler mySuccessHandler() {
        return (request, response, authentication) -> {
            // Aquí defines qué hacer después del login exitoso
            response.sendRedirect("/v1/session"); // redirige a tu página principal
        };
    }

    //los datos de la sesión
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}
