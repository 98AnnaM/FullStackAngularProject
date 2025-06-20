import {Component, OnInit} from '@angular/core';
import {Offer} from '../../types/offer';
import {ApiService} from '../../api.service';
import {CommonModule} from '@angular/common';
import {LoaderComponent} from '../../shared/loader/loader.component';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-offer-list',
  standalone: true,
  imports: [CommonModule, LoaderComponent, RouterLink],
  templateUrl: './offer-list.component.html',
  styleUrl: './offer-list.component.css'
})
export class OfferListComponent implements OnInit{
  offers: Offer[] = [];
  isLoading: boolean = true;

  constructor(private apiService: ApiService) {
  }

  ngOnInit(): void {
    this.apiService.getOffers().subscribe(offers => {
        this.offers = offers;
        this.isLoading = false;
      }
    );

    }
}
