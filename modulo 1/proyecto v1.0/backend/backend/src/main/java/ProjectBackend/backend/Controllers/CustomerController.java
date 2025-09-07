package ProjectBackend.backend.Controllers;


//este controlador será utilizado para aprender sobre la seguridad de springboot security

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1") //enlazar las url
public class CustomerController {

    @Autowired
    private SessionRegistry sessionRegistry;

    //para salir de la sesión se debe usar la siguiente url http://localhost:8081/logout


    @GetMapping("/index")
    public String index() {
        return "Hello World witch secured";
    }


    @GetMapping("/index2")
    public String index2() {
        return "Hello World Not Secured";
    }

    @GetMapping("/inf")
    public String inf() {
        return "Welcome to the inference system\nurl available: null";
    }

    @GetMapping("/session")
    public ResponseEntity<?> getDetailsSession() {

            String sessionId = "";
            User userDetails = null;
            List<Object> sessions = sessionRegistry.getAllPrincipals();

            for (Object session : sessions) {
                if (session instanceof User) {
                    userDetails = (User) session;
                }

                 //Nos devuelve la información de todas las sesiones
                List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(session, false);

                for (SessionInformation sessionInformation : sessionInformations) {
                    sessionId = sessionInformation.getSessionId();
                }

            }

        Map<String, Object> response = new HashMap<>();
        response.put("response", "Hello World");
        response.put("sessionId", sessionId);
        response.put("sesionUser", userDetails);

        return ResponseEntity.ok(response);
    }
}
