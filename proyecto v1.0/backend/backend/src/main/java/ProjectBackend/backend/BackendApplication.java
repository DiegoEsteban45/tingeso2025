package ProjectBackend.backend;

import ProjectBackend.backend.Entities.ERole;
import ProjectBackend.backend.Entities.RoleEntity;
import ProjectBackend.backend.Entities.UserEntity;
import ProjectBackend.backend.Repositories.UserRepository;
import ProjectBackend.backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Bean
        // 👈 sin esto nunca se ejecutaba
    CommandLineRunner init() {
        return args -> {

            UserEntity user = UserEntity.builder()
                    .email("Diego@gmail.com")
                    .username("Diego")
                    .rut("12345678")
                    .password(passwordEncoder.encode("1234"))
                    .roles(Set.of(RoleEntity.builder()
                            .name(ERole.ADMIN) // no hace falta ERole.valueOf(...)
                            .build()))
                    .build();

            UserEntity user2 = UserEntity.builder()
                    .email("Esteban@gmail.com")
                    .username("Esteban")
                    .rut("12345678")
                    .password(passwordEncoder.encode("1234"))
                    .roles(Set.of(RoleEntity.builder()
                            .name(ERole.CUSTOMER)
                            .build()))
                    .build();

            UserEntity user3 = UserEntity.builder()
                    .email("Abarca@gmail.com")
                    .username("Abarca")
                    .rut("12345678")
                    .password(passwordEncoder.encode("1234"))
                    .roles(Set.of(RoleEntity.builder()
                            .name(ERole.INVITED)
                            .build()))
                    .build();

            if (userService.createUser(user))   System.out.println("usuario creado Diego");
            if (userService.createUser(user2))  System.out.println("usuario creado Esteban");
            if (userService.createUser(user3))  System.out.println("usuario creado Abarca");
        };
    }
}

