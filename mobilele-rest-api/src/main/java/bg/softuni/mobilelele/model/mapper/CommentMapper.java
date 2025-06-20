package bg.softuni.mobilelele.model.mapper;

import bg.softuni.mobilelele.model.dto.CommentViewDto;
import bg.softuni.mobilelele.model.entity.CommentEntity;
import bg.softuni.mobilelele.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "id", target = "commentId")
    @Mapping(source = "textContent", target = "message")
    @Mapping(target = "user", expression = "java(mapAuthorFullName(comment.getAuthor()))")
    @Mapping(source = "created", target = "created")
    CommentViewDto commentEntityToCommentViewDto(CommentEntity comment);

    default String mapAuthorFullName(UserEntity author) {
        return author.getFirstName() + " " + author.getLastName();
    }
}
