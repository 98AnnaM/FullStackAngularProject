import {ValidatorFn} from '@angular/forms';

export function wholeNumberBiggerThenValidator(minValue: number): ValidatorFn{
  return (control) => {
    const input = control.value;

    if (input === null || input === '') {
      return null; // Let `required` validator handle empty values
    }

    const num = Number(input);
    const isInvalid = !Number.isInteger(num) || num < minValue;
    return isInvalid ? { wholeNumberBiggerThenValidator: true } : null;
  };
}
