export interface BackendFormErrors {
  errors: FieldError[];
}

export interface FieldError {
  field: string;
  message: string;
}
