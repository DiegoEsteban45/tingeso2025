package ProjectBackend.backend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "loans")
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único del préstamo

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer; // Cliente que realiza el préstamo

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "tool_id")
//    private ToolEntity tool; // Herramienta que se está prestando

    @Column(nullable = false)
    private LocalDateTime loanDate; // Fecha en que se entrega la herramienta

    @Column(nullable = false)
    private LocalDateTime expectedReturnDate; // Fecha pactada para la devolución

    private LocalDateTime actualReturnDate; // Fecha real en que se devuelve la herramienta

    @Column(nullable = false)
    private BigDecimal rentalFee; // Tarifa de arriendo aplicada al préstamo

    private BigDecimal lateFee; // Multa por atraso en la devolución (si aplica)

    private BigDecimal damageFee; // Penalización por daño a la herramienta (si aplica)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status = LoanStatus.ACTIVE; // Estado del préstamo (Activo, Devuelto, Atrasado, etc.)

    @ManyToOne(optional = false)
    @JoinColumn(name = "registered_by")
    private UserEntity registeredBy; // Usuario (Empleado/Admin) que registró el préstamo

    @ManyToOne
    @JoinColumn(name = "returned_by")
    private UserEntity returnedBy; // Usuario que registró la devolución (si ya fue devuelta)

    @CreationTimestamp
    private LocalDateTime createdAt; // Fecha y hora en que se creó el registro

    @UpdateTimestamp
    private LocalDateTime updatedAt; // Fecha y hora de la última actualización del registro
}
