package ProjectBackend.backend.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO de respuesta para clientes registrados.
 * Contiene únicamente información relevante para el frontend.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private String username;
    private String email;
    private String rut;

    // Opcional: algún mensaje de confirmación
    private String message;
}
