import { Component } from '@angular/core';
import {HomeComponent} from '../home/home.component';
import {BrandsListComponent} from '../brands/brands-list/brands-list.component';
import {OfferListComponent} from '../offers/offer-list/offer-list.component';
import {OfferDetailsComponent} from '../offers/offer-details/offer-details.component';
import {OfferAddComponent} from '../offers/offer-add/offer-add.component';
import {OfferUpdateComponent} from '../offers/offer-update/offer-update.component';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [
    HomeComponent,
    BrandsListComponent,
    OfferListComponent,
    OfferDetailsComponent,
    OfferAddComponent,
    OfferUpdateComponent
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent {

}
