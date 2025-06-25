import {Component, OnInit} from '@angular/core';
import {OfferView} from '../../types/offerView';
import {CommonModule} from '@angular/common';
import {LoaderComponent} from '../../shared/loader/loader.component';
import {RouterLink} from '@angular/router';
import {OffersService} from '../offers.service';

@Component({
  selector: 'app-offer-list',
  standalone: true,
  imports: [CommonModule, LoaderComponent, RouterLink],
  templateUrl: './offer-list.component.html',
  styleUrl: './offer-list.component.css'
})
export class OfferListComponent implements OnInit{
  offers: OfferView[] = [];
  isLoading: boolean = true;

  constructor(private offerService: OffersService) {
  }

  ngOnInit(): void {
    this.offerService.getOffers().subscribe(offers => {
        this.offers = offers;
        this.isLoading = false;
      }
    );

    }
}
