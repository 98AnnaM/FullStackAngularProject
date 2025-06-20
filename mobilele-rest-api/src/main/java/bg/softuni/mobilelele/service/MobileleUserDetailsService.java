package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.model.entity.UserEntity;
import bg.softuni.mobilelele.model.entity.UserRoleEntity;
import bg.softuni.mobilelele.model.user.MobileleUserDetails;
import bg.softuni.mobilelele.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


//NOTE: This is not annotated as @Service, because we will return it as a @Bean.
public class MobileleUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(MobileleUserDetailsService.class);

    private final UserRepository userRepository;

    public MobileleUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Trying to load user by username: {}", username);

        return this.userRepository
                .findByEmail(username)
                .map(this::map)
                .orElseThrow(() -> {
                    logger.error("User with email {} not found.", username); // Add this line for logging
                    return new UsernameNotFoundException("User with email " + username + " not found.");
                });
    }

    private UserDetails map(UserEntity userEntity) {
        logger.info("Mapping user details for user with email: {}", userEntity.getEmail()); // Add this line for logging

        return new MobileleUserDetails(
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity
                        .getUserRoles()
                        .stream()
                        .map(this::map)
                        .toList());
    }

    private GrantedAuthority map(UserRoleEntity userRole) {
        logger.info("Mapping user role: {}", userRole.getUserRole()); // Add this line for logging

        return new SimpleGrantedAuthority("ROLE_" + userRole.getUserRole().name());
    }
}
