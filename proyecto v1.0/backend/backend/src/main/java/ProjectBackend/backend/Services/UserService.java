package ProjectBackend.backend.Services;


import ProjectBackend.backend.Entities.UserEntity;
import ProjectBackend.backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean createUser(UserEntity user) {
        // Validar si el username ya existe
        if (userRepository.existsByUsername(user.getUsername())) {
            return false;
        }

        // Validar si el email ya existe
        if (userRepository.existsByEmail(user.getEmail())) {
            return false;
        }

        // Encriptar contraseña

        // Guardar usuario
        userRepository.save(user);

        return true;
    }

    public boolean deleteUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }

}
