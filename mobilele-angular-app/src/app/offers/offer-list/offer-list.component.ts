import { Component, OnInit } from '@angular/core';
import { OfferView } from '../../types/offerView';
import { CommonModule } from '@angular/common';
import { LoaderComponent } from '../../shared/loader/loader.component';
import { Router, RouterLink } from '@angular/router';
import { OffersService } from '../offers.service';

@Component({
  selector: 'app-offer-list',
  standalone: true,
  imports: [CommonModule, LoaderComponent, RouterLink],
  templateUrl: './offer-list.component.html',
  styleUrl: './offer-list.component.css'
})
export class OfferListComponent implements OnInit {
  offers: OfferView[] = [];
  isLoading: boolean = true;

  constructor(private offerService: OffersService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.offerService.getOffers().subscribe({
      next: (offers) => {
        this.offers = offers;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        const status = err?.status;
        this.router.navigate(['/error', status || '500']);
      }
    });
  }
}
