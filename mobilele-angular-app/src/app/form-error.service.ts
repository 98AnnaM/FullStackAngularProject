import { Injectable } from '@angular/core';
import { AbstractControl, FormGroup } from '@angular/forms';

@Injectable({ providedIn: 'root' })
export class FormErrorService {
  mapBackendErrorsToForm(
    form: FormGroup,
    errors: { [key: string]: string[] },
    fieldMappings: { [key: string]: string } = {}
  ): void {
    Object.entries(errors).forEach(([field, messages]) => {
      const formFieldPath = fieldMappings[field] || field;
      const control = this.getControl(form, formFieldPath);

      if (control) {
        control.setErrors({ backend: messages.join(', ') });
        control.markAsTouched(); // so the error appears immediately
      }
    });
  }

  private getControl(form: FormGroup, path: string): AbstractControl | null {
    return path.split('.').reduce<AbstractControl | null>((acc, part) => {
      if (!acc) return null;
      return acc.get(part);
    }, form);
  }
}
