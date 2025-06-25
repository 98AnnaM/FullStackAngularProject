import { Component } from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {emailValidator} from '../../validators/email.validator';
import {DOMAINS} from '../../constants';
import {matchPasswordsValidator} from '../../validators/match-passwords.validator';
import {Router} from '@angular/router';
import {UserService} from '../user.service';
import {FormErrorService} from '../../form-error.service';
import {UserRegisterRequest} from '../../types/userRegisterRequest';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  constructor(private userService: UserService,
              private formErrorServIce: FormErrorService,
              private router: Router) {}

  form = new FormGroup({
    firstName: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(15)]),
    lastName: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(15)]),
    email: new FormControl('', [Validators.required, emailValidator(DOMAINS)]),
    passwordGroup: new FormGroup({
        password: new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(10)]),
        rePassword: new FormControl('', [Validators.required])
      },
      {validators: [matchPasswordsValidator('password', 'rePassword')],}
    ),
  });

  register() {
    if(this.form.invalid) {
      return;
    }

    const v = this.form.value!;
    const userRegisterRequest: UserRegisterRequest = {
      firstName: v.firstName!,
      lastName: v.lastName!,
      email: v.email!,
      password: v.passwordGroup?.password!,
      confirmPassword: v.passwordGroup?.rePassword!,
    };

    this.userService.register(userRegisterRequest)
      .subscribe({
        next: () => this.router.navigate(['/users/login']),
        error: err => {
          console.log(err);
          if (err.status === 400 && err.error?.errors) {
            this.formErrorServIce.mapBackendErrorsToForm(this.form, err.error.errors, {
              password: 'passwordGroup.password',
              confirmPassword: 'passwordGroup.rePassword'
            });

          }
        }
      });
  }
}
