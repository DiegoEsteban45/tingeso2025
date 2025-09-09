package ProjectBackend.backend;

import ProjectBackend.backend.Entities.ERole;
import ProjectBackend.backend.Entities.RoleEntity;
import ProjectBackend.backend.Entities.UserEntity;
import ProjectBackend.backend.Repositories.RoleRepository;
import ProjectBackend.backend.Repositories.UserRepository;
import ProjectBackend.backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Set;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Crear roles si no existen
            Arrays.stream(ERole.values()).forEach(erole -> {
                roleRepository.findByName(erole).orElseGet(() -> {
                    RoleEntity role = RoleEntity.builder().name(erole).build();
                    return roleRepository.save(role);
                });
            });

            // 2. Buscar roles desde la BD
            RoleEntity adminRole = roleRepository.findByName(ERole.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Role ADMIN not found"));
            RoleEntity customerRole = roleRepository.findByName(ERole.CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Role CUSTOMER not found"));
            RoleEntity invitedRole = roleRepository.findByName(ERole.INVITED)
                    .orElseThrow(() -> new RuntimeException("Role INVITED not found"));

            // 3. Crear usuarios con roles existentes
            UserEntity user = UserEntity.builder()
                    .email("Diego@gmail.com")
                    .username("Diego")
                    .rut("12345678")
                    .password(passwordEncoder.encode("1234"))
                    .roles(Set.of(adminRole)) // 👈 ya vienen de la BD
                    .enabled(true)
                    .build();

            UserEntity user2 = UserEntity.builder()
                    .email("Esteban@gmail.com")
                    .username("Esteban")
                    .rut("12345678")
                    .password(passwordEncoder.encode("1234"))
                    .roles(Set.of(customerRole))
                    .enabled(true)
                    .build();

            UserEntity user3 = UserEntity.builder()
                    .email("Abarca@gmail.com")
                    .username("Abarca")
                    .rut("12345678")
                    .password(passwordEncoder.encode("1234"))
                    .roles(Set.of(invitedRole))
                    .enabled(true)
                    .build();

            if (userRepository.findByEmail(user.getEmail()).isEmpty()) userRepository.save(user);
            if (userRepository.findByEmail(user2.getEmail()).isEmpty()) userRepository.save(user2);
            if (userRepository.findByEmail(user3.getEmail()).isEmpty()) userRepository.save(user3);

            System.out.println("Roles y usuarios iniciales cargados correctamente 🚀");
        };
    }
}

