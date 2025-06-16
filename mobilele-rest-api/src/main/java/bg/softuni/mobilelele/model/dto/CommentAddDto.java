package bg.softuni.mobilelele.model.dto;

import javax.validation.constraints.NotBlank;

public class CommentAddDto {

    @NotBlank
    private String message;

    public CommentAddDto() {
    }

    public CommentAddDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}