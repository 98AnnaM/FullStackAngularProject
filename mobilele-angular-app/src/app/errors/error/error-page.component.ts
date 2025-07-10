import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-error-page-not-found',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './error-page.component.html',
  styleUrl: './error-page.component.css'
})
export class ErrorPageComponent {
  errorCode = '404';
  message = 'Ooops! Page Not Found';

  constructor(private router: Router) {
    const nav = this.router.getCurrentNavigation();
    if (nav?.extras?.state) {
      this.errorCode = (nav.extras.state['errorCode'] ?? this.errorCode).toString();
      console.log(this.errorCode);

      this.message = nav.extras.state['message'] ?? this.message;
      console.log(this.message);
    }
  }
}
