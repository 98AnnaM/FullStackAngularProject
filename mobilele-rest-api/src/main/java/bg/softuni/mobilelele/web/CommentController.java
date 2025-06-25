package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.CommentAddDto;
import bg.softuni.mobilelele.model.dto.CommentServiceModel;
import bg.softuni.mobilelele.model.dto.CommentViewDto;
import bg.softuni.mobilelele.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/offers/{offerId}/comments")
    public ResponseEntity<List<CommentViewDto>> getComments(
            @PathVariable Long offerId,
            @AuthenticationPrincipal UserDetails principal) {
        return ResponseEntity.ok(commentService.getComments(offerId, principal != null ? principal.getUsername() : null));
    }

    @PostMapping("/offers/{offerId}/comments")
    public ResponseEntity<CommentViewDto> newComment(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long offerId,
            @RequestBody @Valid CommentAddDto addCommentDto) {

        if (principal == null) {
            return ResponseEntity.status(403).build();
        }

        CommentServiceModel commentServiceModel = new CommentServiceModel();
        commentServiceModel.setMessage(addCommentDto.getMessage());
        commentServiceModel.setCreatorEmail(principal.getUsername());
        commentServiceModel.setOfferId(offerId);

        CommentViewDto newComment = commentService.createComment(commentServiceModel);
        URI locationOfNewComment =
                URI.create(String.format("/offers/%s/comments/%s", offerId, newComment.getCommentId()));

        return ResponseEntity
                .created(locationOfNewComment)
                .body(newComment);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommentViewDto> deleteComment(
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal UserDetails principal) {

        if (principal != null && commentService.isAuthorOrAdmin(principal.getUsername(), commentId)) {
            CommentViewDto deleted = commentService.deleteComment(commentId, principal.getUsername());
            return ResponseEntity.ok(deleted);
        }
        return ResponseEntity.status(403).build();
    }
}
