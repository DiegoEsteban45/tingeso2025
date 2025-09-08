package ProjectBackend.backend.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 100)
    private String nombre;

    @NotBlank
    @Size(min = 8, max = 12)
    private String rut;

    @NotBlank
    @Size(min = 1, max = 200)
    private String direccion;

    @NotBlank
    @Size(min = 8, max = 15)
    private String telefono;

    @Enumerated(EnumType.STRING)
    private CustomerEstate estate = CustomerEstate.ACTIVE;

    // Relación 1:1 con UserEntity
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

}

