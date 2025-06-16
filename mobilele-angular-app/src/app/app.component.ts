import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {HeaderComponent} from './core/header/header.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {OfferListComponent} from './offers/offer-list/offer-list.component';
import {OfferAddComponent} from './offers/offer-add/offer-add.component';
import {OfferUpdateComponent} from './offers/offer-update/offer-update.component';
import {HomeComponent} from './home/home.component';
import {BrandsListComponent} from './brands/brands-list/brands-list.component';
import {OfferDetailsComponent} from './offers/offer-details/offer-details.component';
import {FooterComponent} from './core/footer/footer.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, LoginComponent, RegisterComponent, OfferListComponent, OfferAddComponent, OfferUpdateComponent, HomeComponent, BrandsListComponent, OfferDetailsComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'mobilele-angular-app';
}
