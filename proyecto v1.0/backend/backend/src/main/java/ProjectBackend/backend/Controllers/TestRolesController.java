package ProjectBackend.backend.Controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
public class TestRolesController {

    @GetMapping("/accesAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String accessAdmin() {
        return "hola administrador";
    }

    @GetMapping("/accessCustomer")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public String accessCustomer() {
        return "hola Cliente";
    }

    @GetMapping("/accessInvited")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER') or hasRole('EMPLOYEE') or hasRole('INVITED')")
    public String accessInvited() {
        return "hola invitado";
    }
}

