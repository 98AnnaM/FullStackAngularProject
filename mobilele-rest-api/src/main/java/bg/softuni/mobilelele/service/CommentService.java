package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.model.dto.CommentServiceModel;
import bg.softuni.mobilelele.model.dto.CommentViewDto;
import bg.softuni.mobilelele.model.entity.CommentEntity;
import bg.softuni.mobilelele.model.entity.OfferEntity;
import bg.softuni.mobilelele.model.entity.UserEntity;
import bg.softuni.mobilelele.repository.CommentRepository;
import bg.softuni.mobilelele.repository.OfferRepository;
import bg.softuni.mobilelele.web.exception.ObjectNotFoundException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class CommentService {

    private final OfferRepository offerRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;

    public CommentService(OfferRepository offerRepository, UserService userService, CommentRepository commentRepository) {
        this.offerRepository = offerRepository;
        this.userService = userService;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Page<CommentViewDto> getComments(Long offerId, String principalEmail, Pageable pageable) {
        if (!offerRepository.existsById(offerId)) {
            throw new ObjectNotFoundException("Offer with id " + offerId + " was not found!");
        }

        return commentRepository
            .findAllByOffer_IdOrderByCreatedDesc(offerId, pageable)
            .map(comment -> mapAsComment(comment, principalEmail));
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

        UserEntity creator = userService.getUserByEmail(commentServiceModel.getCreatorEmail());

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

    public void deleteAllCommentsByOfferId(Long offerId) {
        this.commentRepository.deleteAllByOffer_id(offerId);
    }

    public boolean isAuthorOrAdmin(String userEmail, Long commentId) {
        boolean isOwner = commentRepository.
                findById(commentId).
                filter(c -> c.getAuthor().getEmail().equals(userEmail)).
                isPresent();

        return isOwner || this.userService.isUserAdmin(userEmail);
    }

}
