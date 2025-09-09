package ProjectBackend.backend.Controllers;

import ProjectBackend.backend.Services.registration.AdminRegisterStrategyService;
import ProjectBackend.backend.dto.request.UserRequestDTO;
import ProjectBackend.backend.dto.response.UserResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminRegisterController {

    @Autowired
    private AdminRegisterStrategyService adminRegisterStrategy;

    // Solo accesible por admins 👇
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUserByAdmin(
            @Valid @RequestBody UserRequestDTO dto) {

        UserResponseDTO response = adminRegisterStrategy.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

