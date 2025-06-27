import { Component } from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-error-page-not-found',
  standalone: true,
  imports: [],
  templateUrl: './error-page.component.html',
  styleUrl: './error-page.component.css'
})
export class ErrorPageComponent {
  errorCode = '404';
  message = 'Ooops! Page Not Found';

  constructor(private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      this.errorCode = params['errorCode'] || '404';

      if (this.errorCode === '403') {
        this.message = 'Access Denied';
      } else {
        this.message = 'Ooops! Page Not Found';
      }
    });
  }
}

