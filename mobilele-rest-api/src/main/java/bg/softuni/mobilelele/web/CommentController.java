package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.CommentAddDto;
import bg.softuni.mobilelele.model.dto.CommentServiceModel;
import bg.softuni.mobilelele.model.dto.CommentViewDto;
import bg.softuni.mobilelele.service.CommentService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/offers/{offerId}/comments")
    public ResponseEntity<Page<CommentViewDto>> getComments(
        @PathVariable Long offerId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @AuthenticationPrincipal UserDetails principal) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CommentViewDto> result = commentService.getComments(offerId,
            principal != null ? principal.getUsername() : null,
            pageable);
        return ResponseEntity.ok(result);
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
