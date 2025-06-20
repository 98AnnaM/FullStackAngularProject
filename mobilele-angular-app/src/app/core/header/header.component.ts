import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {UserService} from '../../user/user.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  constructor(private userService: UserService, private router: Router) {}

  get isLogged(): boolean {
    return this.userService.isLogged;
  }

  get username(): string {
    const user = this.userService.user;
    return user ? `${user.firstName} ${user.lastName}` : '';
  }

  logout() {
    this.userService.logout();
    this.router.navigate(['/users/login']);
  }
}
