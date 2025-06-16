package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.model.dto.UserRegisterDto;
import bg.softuni.mobilelele.model.entity.UserEntity;
import bg.softuni.mobilelele.model.enums.UserRoleEnum;
import bg.softuni.mobilelele.model.mapper.UserMapper;
import bg.softuni.mobilelele.repository.UserRepository;
import bg.softuni.mobilelele.repository.UserRoleRepository;
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
    private final UserDetailsService userDetailsService;
    private final EmailService emailService;
    private final UserRoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, UserDetailsService userDetailsService, EmailService emailService, UserRoleRepository repository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
        this.roleRepository = repository;
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
        return this.emailExists(email);
    }
}
