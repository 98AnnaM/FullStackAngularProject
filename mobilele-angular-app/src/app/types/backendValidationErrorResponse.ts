export interface BackendValidationErrorResponse {
  errors: FieldError[];
}

export interface FieldError {
  field: string;
  message: string;
}
