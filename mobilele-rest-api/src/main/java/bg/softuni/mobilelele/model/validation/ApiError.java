package bg.softuni.mobilelele.model.validation;

import java.util.ArrayList;
import java.util.List;

public class ApiError {
    private List<FieldError> errors = new ArrayList<>();

    public void addError(String field, String message) {
        errors.add(new FieldError(field, message));
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldError> errors) {
        this.errors = errors;
    }

    public static class FieldError {
        private String field;
        private String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}
