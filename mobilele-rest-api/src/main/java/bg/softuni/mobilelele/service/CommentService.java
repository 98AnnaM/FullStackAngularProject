package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.model.dto.CommentServiceModel;
import bg.softuni.mobilelele.model.dto.CommentViewDto;
import bg.softuni.mobilelele.model.entity.CommentEntity;
import bg.softuni.mobilelele.model.entity.OfferEntity;
import bg.softuni.mobilelele.model.entity.UserEntity;
import bg.softuni.mobilelele.model.enums.UserRoleEnum;
import bg.softuni.mobilelele.repository.CommentRepository;
import bg.softuni.mobilelele.repository.OfferRepository;
import bg.softuni.mobilelele.repository.UserRepository;
import bg.softuni.mobilelele.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentService(OfferRepository offerRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public List<CommentViewDto> getComments(Long offerId, String principalEmail) {
        Optional<OfferEntity> offerOptional = this.offerRepository.findById(offerId);

        if (offerOptional.isEmpty()) {
            throw new ObjectNotFoundException("Offer with id " + offerId + " was not found!");
        }
        return offerOptional.get()
                .getComments().stream()
                .map(comment -> mapAsComment(comment, principalEmail))
                .collect(Collectors.toList());
    }

    private CommentViewDto mapAsComment(CommentEntity commentEntity, String principalEmail) {
        CommentViewDto commentViewDto = new CommentViewDto();

        commentViewDto.setCommentId(commentEntity.getId())
                .setMessage(commentEntity.getTextContent())
                .setUser(commentEntity.getAuthor().getFirstName() + " " + commentEntity.getAuthor().getLastName())
                .setCreated(commentEntity.getCreated())
                .setCanDelete(principalEmail != null && isAuthorOrAdmin(principalEmail, commentEntity.getId()));

        return commentViewDto;
    }

    public CommentViewDto createComment(CommentServiceModel commentServiceModel) {
        OfferEntity offer = offerRepository.findById(commentServiceModel.getOfferId())
                .orElseThrow(() -> new UnsupportedOperationException("Offer with id " + commentServiceModel.getOfferId() + " not found!"));

        UserEntity creator = userRepository.findByEmail(commentServiceModel.getCreatorEmail())
                .orElseThrow(() -> new UnsupportedOperationException("User with email " + commentServiceModel.getCreatorEmail() + " not found!"));

        CommentEntity newComment = new CommentEntity();
        newComment.setTextContent(commentServiceModel.getMessage());
        newComment.setCreated(LocalDateTime.now());
        newComment.setAuthor(creator);
        newComment.setOffer(offer);

        CommentEntity savedComment = commentRepository.save(newComment);
        return mapAsComment(savedComment, creator.getEmail());
    }

    public CommentViewDto deleteComment(Long commentId, String principalEmail) {
        CommentEntity deleted = commentRepository.findById(commentId)
                .orElseThrow(() -> new ObjectNotFoundException("Comment with id " + commentId + " not found!"));

        commentRepository.deleteById(commentId);
        return mapAsComment(deleted, principalEmail);
    }

    public boolean isAuthorOrAdmin(String userEmail, Long commentId) {
        boolean isOwner = commentRepository.
                findById(commentId).
                filter(c -> c.getAuthor().getEmail().equals(userEmail)).
                isPresent();

        if (isOwner) {
            return true;
        }

        return userRepository
                .findByEmail(userEmail)
                .filter(this::isAdmin)
                .isPresent();
    }

    private boolean isAdmin(UserEntity user) {
        return user.getUserRoles().
                stream().
                anyMatch(r -> r.getUserRole() == UserRoleEnum.ADMIN);
    }
}
