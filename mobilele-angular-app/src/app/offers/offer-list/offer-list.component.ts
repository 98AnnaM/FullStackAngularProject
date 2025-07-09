import { Component, OnInit } from '@angular/core';
import { OfferView } from '../../types/offerView';
import { CommonModule } from '@angular/common';
import { LoaderComponent } from '../../shared/loader/loader.component';
import { RouterLink } from '@angular/router';
import { OffersService } from '../offers.service';
import { ErrorService } from '../../errors/error.service';
import { OfferCardComponent } from '../offer-card/offer-card.component';

@Component({
  selector: 'app-offer-list',
  standalone: true,
  imports: [CommonModule, LoaderComponent, RouterLink, OfferCardComponent],
  templateUrl: './offer-list.component.html',
  styleUrl: './offer-list.component.css'
})
export class OfferListComponent implements OnInit {
  offers: OfferView[] = [];
  isLoading: boolean = true;

  constructor(private offerService: OffersService,
              private errorService: ErrorService) {}

  ngOnInit(): void {
    this.offerService.getOffers().subscribe({
      next: (offers) => {
        this.offers = offers;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        this.errorService.navigateToErrorPage(err);
      }
    });
  }
}
