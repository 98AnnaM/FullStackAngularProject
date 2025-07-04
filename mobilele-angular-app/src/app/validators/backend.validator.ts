import {ValidatorFn} from '@angular/forms';

export function backendValidator(): ValidatorFn{
  return (control) => {
    const backendError: string | undefined = control.errors?.['backend'];
    return backendError ? { backendValidator: backendError } : null;
  };
}
