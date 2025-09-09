package ProjectBackend.backend.Services.registration;

import ProjectBackend.backend.Entities.ERole;
import ProjectBackend.backend.Entities.RoleEntity;
import ProjectBackend.backend.Entities.UserEntity;
import ProjectBackend.backend.Repositories.RoleRepository;
import ProjectBackend.backend.Repositories.UserRepository;
import ProjectBackend.backend.dto.request.UserRequestDTO;
import ProjectBackend.backend.dto.response.UserResponseDTO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Data
@Service
public class CustomerRegisterStrategyService implements RegisterStrategy {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserResponseDTO register(UserRequestDTO dto) {
        // Validar email o username ya existente
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Verificar password confirm (si tienes CustomerUserRequestDTO)
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // Buscar el rol existente en la base
        RoleEntity customerRole = roleRepository.findByName(ERole.CUSTOMER)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: CUSTOMER"));

        // Crear usuario cliente
        UserEntity user = UserEntity.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .rut(dto.getRut())
                .roles(Set.of(customerRole)) // asignamos el rol existente
                .enabled(true)
                .build();

        userRepository.save(user);

        // Devolver response DTO
        return UserResponseDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .rut(user.getRut())
                .message("Customer registered successfully")
                .build();
    }
}
