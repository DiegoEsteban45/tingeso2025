package ProjectBackend.backend.Repositories;

import ProjectBackend.backend.Entities.ERole;
import ProjectBackend.backend.Entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(ERole name);
    Optional<RoleEntity> findByName(String name);
}
