package ProjectBackend.backend.Services.registration;

import ProjectBackend.backend.dto.request.UserRequestDTO;
import ProjectBackend.backend.dto.response.UserResponseDTO;


public interface RegisterStrategy {
    UserResponseDTO registerUser(UserRequestDTO dto);
}