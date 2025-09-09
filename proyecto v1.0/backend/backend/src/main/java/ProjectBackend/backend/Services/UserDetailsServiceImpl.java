package ProjectBackend.backend.Services;

import ProjectBackend.backend.Entities.UserEntity;
import ProjectBackend.backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { //en realidad es por email no username

        // Buscamos el usuario por email en lugar de username
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The user " + email + " does not exist"));

        // Convertimos los roles del usuario a GrantedAuthority
        Collection<? extends GrantedAuthority> authorities = userEntity.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .collect(Collectors.toSet());

        // Retornamos un objeto User de Spring Security usando email como username
        return new org.springframework.security.core.userdetails.User(
                userEntity.getEmail(), // < email
                userEntity.getPassword(),
                true, // accountNonExpired
                true, // accountNonLocked
                true, // credentialsNonExpired
                true, // enabled
                authorities
        );
    }
}
