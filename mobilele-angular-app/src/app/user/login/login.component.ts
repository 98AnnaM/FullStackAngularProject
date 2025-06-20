import { Component } from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import {UserService} from '../user.service';
import {Router} from '@angular/router';
import {DOMAINS} from '../../constants';
import {EmailDirective} from '../../directives/email.directive';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    EmailDirective
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  domains = DOMAINS;
  badCredentials: boolean = false;

  constructor(private userService: UserService, private router: Router) {}

  login(form: NgForm): void {
    if (form.invalid) {
      console.error('Invalid login form!');
      return;
    }

    const { email, password } = form.value;

    this.userService.login(email, password).subscribe({
      next: () => {
        this.badCredentials = false;
        this.router.navigate(['/home']);
      },
      error: () => {
        this.badCredentials = true;
      }
    });
  }
}
