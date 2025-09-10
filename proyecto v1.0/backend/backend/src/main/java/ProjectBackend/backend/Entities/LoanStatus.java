package ProjectBackend.backend.Entities;

public enum LoanStatus {
    ACTIVE,     // Préstamo vigente
    RETURNED,   // Herramienta devuelta correctamente
    LATE,       // Préstamo con atraso en la devolución
    DAMAGED,    // Herramienta devuelta con daños
    CLOSED      // Préstamo finalizado (con multas pagadas o penalizaciones aplicadas)
}

