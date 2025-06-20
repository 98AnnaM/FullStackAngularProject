import { Component } from '@angular/core';
import {UserService} from '../user/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  constructor(private userService: UserService, private router: Router) {}

  get isLogged(): boolean {
    return this.userService.isLogged;
  }

  get username(): string {
    const user = this.userService.user;
    return user ? `${user.firstName} ${user.lastName}` : '';
  }

}
