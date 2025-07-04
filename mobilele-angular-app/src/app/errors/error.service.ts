import { Injectable } from '@angular/core';
import { AbstractControl, FormGroup } from '@angular/forms';
import { BackendFormErrors, FieldError } from '../types/backendFormErrors';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({providedIn: 'root'})
export class ErrorService {

  constructor(private router: Router) {
  }

  handleHttpPostFormError(
    err: HttpErrorResponse,
    form: FormGroup,
    mapField?: (backendField: string) => string
  ): void {
    if (err.status === 400 && err.error?.errors) {
      this.addBackendValidationErrorsToEachControl(form, err.error, mapField);
    } else {
      this.navigateToErrorPage(err);
    }
  }

  navigateToErrorPage(err: HttpErrorResponse) {
    const errorCode = err?.status ?? 500;
    const message: string = this.getErrorMessage(errorCode);
    this.router.navigate(['/error'], {
      state: { errorCode, message }
    });
  }

  private getErrorMessage(errorCode: number) : string {
    switch (errorCode) {
      case 400: return  'Bad Request - Invalid data sent.';
      case 401: return  'Unauthorized Access - Please login.';
      case 403: return 'Access Denied.';
      case 500: return 'Internal Server Error - Please try again later.';
      default: return  'Ops! Page Not Found';
    }
  }

  private addBackendValidationErrorsToEachControl(
    form: FormGroup,
    backendErrors: BackendFormErrors,
    mapField?: (backendField: string) => string
  ): void {
    backendErrors.errors.forEach(({field, message}: FieldError) => {
      const controlPath = mapField ? mapField(field) : field;
      const control = this.getControl(form, controlPath);
      if (control) {
        const existingErrors = control.errors || {};
        control.setErrors({ ...existingErrors, backend: message });
        control.markAsTouched();
      }
    });
  }

  private getControl(form: FormGroup, controlPath: string): AbstractControl | null {
    const parts = controlPath.split('.');

    let control: AbstractControl | null = form;
    for (const part of parts) {
      if (!control) {
        return null;
      }
      control = control.get(part);
    }
    return control;
  }
}
