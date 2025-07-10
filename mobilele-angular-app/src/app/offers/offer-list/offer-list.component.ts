import { Component, OnInit } from '@angular/core';
import { OfferView } from '../../types/offerView';
import { CommonModule } from '@angular/common';
import { LoaderComponent } from '../../shared/loader/loader.component';
import { RouterLink } from '@angular/router';
import { OffersService } from '../offers.service';
import { ErrorService } from '../../errors/error.service';
import { OfferCardComponent } from '../offer-card/offer-card.component';
import { PaginationComponent } from '../../pagination/pagination.component';
import { Page } from '../../types/page';

@Component({
  selector: 'app-offer-list',
  standalone: true,
  imports: [CommonModule, LoaderComponent, RouterLink, OfferCardComponent, PaginationComponent],
  templateUrl: './offer-list.component.html',
  styleUrl: './offer-list.component.css'
})
export class OfferListComponent implements OnInit {
  offers: OfferView[] = [];
  isLoading: boolean = true;
  page: number = 0;
  size: number = 5;
  totalPages: number = 0;
  totalElements: number = 0;

  constructor(
    private offerService: OffersService,
    private errorService: ErrorService
  ) {}

  ngOnInit(): void {
    this.fetchOffers(this.page);
  }

  fetchOffers(page: number): void {
    this.isLoading = true;
    this.offerService.getOffers(page, this.size).subscribe({
      next: (response: Page<OfferView>) => {
        this.offers = response.content;
        this.totalPages = response.totalPages;
        this.page = response.number;
        this.totalElements = response.totalElements;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        this.errorService.navigateToErrorPage(err);
      }
    });
  }

  loadOffers(newPage: number): void {
    this.fetchOffers(newPage);
  }
}
