import { Component } from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {emailValidator} from '../../utils/email.validator';
import {DOMAINS} from '../../constants';
import {matchPasswordsValidator} from '../../utils/match-passwords.validator';
import {Router} from '@angular/router';
import {UserService} from '../user.service';
import {FormErrorService} from '../../form-error.service';

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

    const {
      firstName,
      lastName,
      email,
      passwordGroup: {password, rePassword} = {},
    } = this.form.value;


    this.userService.register(firstName!, lastName!, email!, password!, rePassword!)
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
