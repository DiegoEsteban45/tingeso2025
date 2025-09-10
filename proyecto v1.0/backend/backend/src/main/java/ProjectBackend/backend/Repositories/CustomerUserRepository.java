package ProjectBackend.backend.Repositories;

import ProjectBackend.backend.Entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerUserRepository  extends JpaRepository<CustomerEntity, Long> {

}
