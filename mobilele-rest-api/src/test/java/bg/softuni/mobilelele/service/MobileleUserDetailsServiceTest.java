package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.model.entity.UserEntity;
import bg.softuni.mobilelele.model.entity.UserRoleEntity;
import bg.softuni.mobilelele.model.enums.UserRoleEnum;
import bg.softuni.mobilelele.model.user.MobileleUserDetails;
import bg.softuni.mobilelele.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class MobileleUserDetailsServiceTest {

    private MobileleUserDetailsService serviceToTest;
    private UserEntity testUser;
    private UserRoleEntity adminRole;
    private UserRoleEntity userRole;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void init(){

        serviceToTest = new MobileleUserDetailsService(mockUserRepository);

        testUser = new UserEntity();

        adminRole = new UserRoleEntity();
        adminRole.setUserRole(UserRoleEnum.ADMIN);

        userRole = new UserRoleEntity();
        userRole.setUserRole(UserRoleEnum.USER);

        testUser.setFirstName("Anna");
        testUser.setLastName("Mileva");
        testUser.setEmail("anna.mileva92@gmail.com");
        testUser.setUserRoles(List.of(adminRole, userRole));
        testUser.setPassword("12345");
    }

    @Test
    void testUserNotFound(){
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("not_existing_email"));
    }

    @Test
    void testUserFound(){

        Mockito.when(mockUserRepository.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));

        MobileleUserDetails userDetails = (MobileleUserDetails) serviceToTest
                .loadUserByUsername(testUser.getEmail());

        Assertions.assertEquals(userDetails.getUsername(), testUser.getEmail());
        Assertions.assertEquals(userDetails.getFirstName(), testUser.getFirstName());
        Assertions.assertEquals(userDetails.getLastName(), testUser.getLastName());
        Assertions.assertEquals(userDetails.getPassword(), testUser.getPassword());
        Assertions.assertEquals(userDetails.getFullName(),
                testUser.getFirstName() + " " + testUser.getLastName());

        Assertions.assertEquals(2, userDetails.getAuthorities().size());

        String actualRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
        String expectedRoles = "ROLE_ADMIN, ROLE_USER";

        Assertions.assertEquals(expectedRoles, actualRoles);



    }

}