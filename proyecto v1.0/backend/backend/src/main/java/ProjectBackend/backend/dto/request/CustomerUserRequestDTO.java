package ProjectBackend.backend.dto.request;

import ProjectBackend.backend.Entities.ERole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO para registrar clientes (usuarios tipo CUSTOMER).
 * No incluye rol ni otros campos sensibles, el rol se asigna automáticamente en el backend.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUserRequestDTO {

    @NotBlank
    @Size(min = 3, max = 100)
    private String username;

    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    @Size(min = 6, max = 20)
    private String passwordConfirm;

    @NotBlank
    @Size(min = 8, max = 12)
    private String rut;

}
