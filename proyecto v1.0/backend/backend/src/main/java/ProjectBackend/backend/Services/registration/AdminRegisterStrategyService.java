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
import java.util.stream.Collectors;

@Data
@Service
public class AdminRegisterStrategyService implements RegisterStrategy {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO registerUser(UserRequestDTO dto) {
        // Validar email o username ya existente
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        // Mapear roles enviados a RoleEntity
        Set<RoleEntity> roles = dto.getRoles().stream()
                .map(roleName -> {
                    ERole enumRole = ERole.valueOf(roleName); // convierte String a Enum
                    return roleRepository.findByName(enumRole)
                            .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
                })
                .collect(Collectors.toSet());

        // Crear usuario
        UserEntity user = UserEntity.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .rut(dto.getRut())
                .roles(roles)
                .enabled(true)
                .build();

        userRepository.save(user);

        // Devolver response DTO
        return UserResponseDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .rut(user.getRut())
                .message("User registered successfully")
                .build();
    }
}

