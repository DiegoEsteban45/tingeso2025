package ProjectBackend.backend.Services.registration;

import ProjectBackend.backend.Entities.*;
import ProjectBackend.backend.Repositories.CustomerUserRepository;
import ProjectBackend.backend.Repositories.RoleRepository;
import ProjectBackend.backend.Repositories.UserRepository;
import ProjectBackend.backend.dto.request.CustomerUserRequestDTO;
import ProjectBackend.backend.dto.request.UserRequestDTO;
import ProjectBackend.backend.dto.response.CustomerUserResponseDTO;
import ProjectBackend.backend.dto.response.UserResponseDTO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
@Service
public class CustomerRegisterStrategyService implements RegisterStrategy {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerUserRepository customerUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserResponseDTO registerUser(UserRequestDTO dto) {
        // Validar email
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Verificar password confirm
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

    public void registerCustomer(CustomerUserRequestDTO cUdto, UserRequestDTO dto) {

        UserEntity user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + dto.getEmail()));

        CustomerEntity customerUser = CustomerEntity.builder()
                .user(user)
                .fullName(cUdto.getUsername())
                .rut(cUdto.getRut())
                .address(cUdto.getAddress())
                .phone(cUdto.getPhone())
                .status(CustomerStatus.ACTIVE)
                .activeLoans(0)
                .outstandingDebt(BigDecimal.ZERO)
                .systemBlocked(false)
                .notes("")
                .loans(new ArrayList<>())
                .build();

        customerUserRepository.save(customerUser);
    }
}
