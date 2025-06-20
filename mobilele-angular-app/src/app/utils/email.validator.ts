import {ValidatorFn} from '@angular/forms';

export function emailValidator(domains: string[]): ValidatorFn{
  const domainStr = domains.join('|');
  const regex: RegExp = new RegExp(`[A-Za-z0-9._]{4,}@example\\.(${domainStr})`);

  return (control) => {
    const isInvalid = control.value==='' || regex.test(control.value);
    return isInvalid ? null : {emailValidator: true};
  };

}
