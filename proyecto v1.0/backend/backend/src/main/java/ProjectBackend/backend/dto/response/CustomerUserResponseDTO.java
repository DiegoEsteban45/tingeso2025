package ProjectBackend.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUserResponseDTO {

    private String username;
    private String email;
    private String rut; // opcional mostrar al frontend
    private String role = "CUSTOMER"; // fijo para clientes

}