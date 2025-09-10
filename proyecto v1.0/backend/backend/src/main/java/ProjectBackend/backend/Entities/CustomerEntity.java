package ProjectBackend.backend.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 100)
    private String fullName;

    @NotBlank
    @Size(min = 8, max = 12)
    private String rut;

    @NotBlank
    @Size(min = 1, max = 200)
    private String address;

    @NotBlank
    @Size(min = 8, max = 15)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerStatus status;

    @Column(nullable = false)
    private BigDecimal outstandingDebt; //Deuda pendiente del cliente

    @Column(nullable = false)
    private Integer activeLoans;

    @Column(nullable = false)
    private Boolean systemBlocked;

    @Size(max = 500)
    private String notes;

    @CreationTimestamp
    private LocalDateTime registrationDate;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<LoanEntity> loans;
}

