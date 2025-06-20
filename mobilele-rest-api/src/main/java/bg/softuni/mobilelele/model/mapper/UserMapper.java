package bg.softuni.mobilelele.model.mapper;

import bg.softuni.mobilelele.model.dto.UserRegisterDto;
import bg.softuni.mobilelele.model.dto.UserViewDto;
import bg.softuni.mobilelele.model.entity.UserEntity;
import bg.softuni.mobilelele.model.enums.UserRoleEnum;
import bg.softuni.mobilelele.model.user.MobileleUserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "active", constant = "true")
    UserEntity userDtoToUserEntity(UserRegisterDto userRegisterDto);

    @Mapping(target = "email", source = "username")
    @Mapping(target = "authorities", expression = "java(mapAuthorities(userDetails.getAuthorities()))")
    UserViewDto userDetailsToUserViewDto(MobileleUserDetails userDetails);

    default List<String> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .map(auth -> auth.substring("ROLE_".length()))
                .collect(Collectors.toList());
    }
}
