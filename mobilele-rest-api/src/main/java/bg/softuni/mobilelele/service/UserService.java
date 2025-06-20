package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.model.dto.UserLoginDto;
import bg.softuni.mobilelele.model.dto.UserRegisterDto;
import bg.softuni.mobilelele.model.dto.UserViewDto;
import bg.softuni.mobilelele.model.entity.UserEntity;
import bg.softuni.mobilelele.model.enums.UserRoleEnum;
import bg.softuni.mobilelele.model.mapper.UserMapper;
import bg.softuni.mobilelele.model.user.MobileleUserDetails;
import bg.softuni.mobilelele.repository.UserRepository;
import bg.softuni.mobilelele.repository.UserRoleRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final UserRoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, EmailService emailService, UserRoleRepository repository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.emailService = emailService;
        this.roleRepository = repository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public UserViewDto authenticate(UserLoginDto loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        MobileleUserDetails userDetails = (MobileleUserDetails) authentication.getPrincipal();

        UserViewDto userViewDto = userMapper.userDetailsToUserViewDto(userDetails);
        userViewDto.setToken(jwtService.generateToken(userDetails.getUsername()));

        return userViewDto;
    }

    public void registerUser(UserRegisterDto userRegisterDto, Locale preferedLocale) {
        UserEntity newUser = userMapper.userDtoToUserEntity(userRegisterDto);
        newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        newUser.setUserRoles(roleRepository.findAll()
                .stream()
                .filter(r -> r.getUserRole() == (UserRoleEnum.USER))
                .collect(Collectors.toList()));

        this.userRepository.save(newUser);

        emailService.sendRegistrationEmail(newUser.getEmail(),
                newUser.getFirstName() + " " + newUser.getLastName(),
                preferedLocale);
    }

    public boolean emailExists(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public boolean isUserAdmin(String userName) {
        return userRepository
                .findByEmail(userName)
                .filter(this::isAdmin)
                .isPresent();
    }

    private boolean isAdmin(UserEntity user) {
        return user.getUserRoles().
                stream().
                anyMatch(r -> r.getUserRole() == UserRoleEnum.ADMIN);
    }
}
