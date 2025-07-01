import { Component } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

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

  constructor(private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      const code = params['errorCode'];
      this.errorCode = code || '404';

      switch (this.errorCode) {
        case '400':
          this.message = 'Bad Request - Invalid data sent.';
          break;
        case '401':
          this.message = 'Unauthorized Access - Please login.';
          break;
        case '403':
          this.message = 'Access Denied.';
          break;
        case '500':
          this.message = 'Internal Server Error - Please try again later.';
          break;
        default:
          this.message = 'Ooops! Page Not Found';
      }
    });
  }
}
