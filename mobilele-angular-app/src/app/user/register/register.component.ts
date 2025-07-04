import { ChangeDetectorRef, Component } from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {emailValidator} from '../../validators/email.validator';
import {DOMAINS} from '../../constants';
import {matchPasswordsValidator} from '../../validators/match-passwords.validator';
import {Router} from '@angular/router';
import {UserService} from '../user.service';
import {ErrorService} from '../../errors/error.service';
import {UserRegisterRequest} from '../../types/userRegisterRequest';
import { LoaderComponent } from '../../shared/loader/loader.component';
import { CommonModule } from '@angular/common';
import { backendValidator } from '../../validators/backend.validator';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    LoaderComponent
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  isLoading: boolean = false;

  constructor(private userService: UserService,
              private errorServIce: ErrorService,
              private router: Router,
              private cd: ChangeDetectorRef) {}

  form = new FormGroup({
    firstName: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(15)]),
    lastName: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(15)]),
    email: new FormControl('', [Validators.required, emailValidator(DOMAINS),backendValidator()]),
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

    this.isLoading = true;

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
        next: () => {
          this.isLoading = false;
          this.router.navigate(['/users/login']);
        },
        error: err => {
          this.errorServIce.handleHttpPostFormError(err, this.form, this.mapRegisterField);
          this.form.markAllAsTouched(); // <-- ADD THIS
          this.cd.detectChanges();
          this.isLoading = false;
        }
      });
  }

  private mapRegisterField(backendField: string): string {
    switch (backendField) {
      case 'password':
        return 'passwordGroup.password';
      case 'confirmPassword':
        return 'passwordGroup.rePassword';
      default:
        return backendField;
    }
  }
}
