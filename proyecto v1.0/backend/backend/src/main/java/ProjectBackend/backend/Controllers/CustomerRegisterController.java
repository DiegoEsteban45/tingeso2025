package ProjectBackend.backend.Controllers;


import ProjectBackend.backend.Entities.ERole;
import ProjectBackend.backend.Repositories.CustomerUserRepository;
import ProjectBackend.backend.Services.registration.CustomerRegisterStrategyService;
import ProjectBackend.backend.dto.request.CustomerUserRequestDTO;
import ProjectBackend.backend.dto.request.UserRequestDTO;
import ProjectBackend.backend.dto.response.UserResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerRegisterController {

    @Autowired
    private CustomerRegisterStrategyService customerRegisterStrategy;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerCustomer(
            @Valid @RequestBody CustomerUserRequestDTO dto) {

        UserRequestDTO userRequest = new UserRequestDTO(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getPasswordConfirm(),
                Set.of(String.valueOf(ERole.CUSTOMER)),
                dto.getRut()
        );

        UserResponseDTO response = customerRegisterStrategy.registerUser(userRequest);
        customerRegisterStrategy.registerCustomer(dto,userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

