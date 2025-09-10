package ProjectBackend.backend.dto.request;

import ProjectBackend.backend.Entities.ERole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUserRequestDTO {

    // Nombre completo
    @NotBlank
    @Size(min = 3, max = 100)
    private String username;

    // Correo electrónico del cliente
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    // Contraseña para login
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    // Confirmación de contraseña
    @NotBlank
    @Size(min = 6, max = 20)
    private String passwordConfirm;

    // RUT del cliente (formato chileno)
    @NotBlank
    @Size(min = 8, max = 12)
    private String rut;

    // Dirección física del cliente
    @NotBlank
    @Size(min = 5, max = 200)
    private String address;

    // Número de teléfono del cliente
    @NotBlank
    @Size(min = 8, max = 15)
    private String phone;
}
