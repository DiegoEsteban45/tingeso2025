package ProjectBackend.backend.Controllers;


//este controlador será utilizado para aprender sobre la seguridad de springboot security

import ProjectBackend.backend.Entities.ERole;
import ProjectBackend.backend.Entities.RoleEntity;
import ProjectBackend.backend.Entities.UserEntity;
import ProjectBackend.backend.Request.CreateUserDTO;
import ProjectBackend.backend.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1") //enlazar las url
public class CustomerController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private UserService userService;

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

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO newUserDTO) {
        try {
            Set<RoleEntity> roleEntities = newUserDTO.getRoles().stream()
                    .map(role -> RoleEntity.builder()
                            .name(ERole.valueOf(role))
                            .build())
                    .collect(Collectors.toSet());

            UserEntity userEntity = UserEntity.builder()
                    .username(newUserDTO.getUsername())
                    .password(passwordEncoder.encode(newUserDTO.getPassword()))
                    .email(newUserDTO.getEmail())
                    .rut(newUserDTO.getRut())
                    .roles(roleEntities)
                    .build();

            if (userService.createUser(userEntity)) {
                CreateUserDTO response = new CreateUserDTO();
                        response.setUsername(userEntity.getUsername());
                        response.setEmail(userEntity.getEmail());
                        response.setRut(userEntity.getRut());

                return ResponseEntity.ok(response);
            }

            return ResponseEntity.badRequest().body("No se pudo crear el usuario");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error invalid parameter");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error en el servidor");
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam String id) {
        try {
            boolean deleted = userService.deleteUser(Long.parseLong(id));

            if (deleted) {
                return ResponseEntity.ok("User has been deleted");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with id: " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting user: " + e.getMessage());
        }
    }


}
