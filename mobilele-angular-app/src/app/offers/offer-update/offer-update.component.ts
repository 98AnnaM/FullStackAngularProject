import { Component, OnInit, ViewChild } from '@angular/core';
import { OfferAddOrEdit } from '../../types/offerAddOrEdit';
import { OffersService } from '../offers.service';
import { BrandsService } from '../../brands/brands.service';
import { ErrorService } from '../../errors/error.service';
import { ActivatedRoute, Router } from '@angular/router';
import { OfferView } from '../../types/offerView';
import { LoaderComponent } from '../../shared/loader/loader.component';
import { combineLatest, Observable, of } from 'rxjs';
import { BrandView } from '../../types/brandView';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TitleCasePipe } from '@angular/common';
import { OfferFormComponent } from '../offer-form/offer-form.component';

@Component({
  selector: 'app-offer-update',
  standalone: true,
  imports: [
    LoaderComponent,
    FormsModule,
    ReactiveFormsModule,
    TitleCasePipe,
    OfferFormComponent
  ],
  templateUrl: './offer-update.component.html',
  styleUrl: './offer-update.component.css'
})
export class OfferUpdateComponent implements OnInit {
  offerId: string = '';
  currentOffer: OfferView | null = null;
  brands: BrandView[] = [];
  isLoading = true;

  @ViewChild(OfferFormComponent) offerForm!: OfferFormComponent;

  constructor(
    private offerService: OffersService,
    private brandsService: BrandsService,
    private errorService: ErrorService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.currentOffer = history.state.offer as OfferView | null;
    this.offerId = this.route.snapshot.params['offerId'];

    const offer$: Observable<OfferView> = this.currentOffer
      ? of(this.currentOffer!)
      : this.offerService.getSingleOffer(this.offerId);

    const brands$: Observable<BrandView[]> = this.brandsService.getBrands();

    this.loadFormData(offer$, brands$);
  }

  save(offerDto: OfferAddOrEdit): void {
    this.isLoading = true;
    this.offerService.updateOffer(this.offerId, offerDto).subscribe({
      next: (createdOffer: OfferView) => {
        this.router.navigate([`/offers/${createdOffer.id}`]);
        this.isLoading = false;
      },
      error: err => {
        this.isLoading = false;
        this.errorService.handleHttpPostFormError(err, this.offerForm.form);
      }
    });
  }

  private loadFormData(offer$: Observable<OfferView>, brands$: Observable<BrandView[]>): void {
    combineLatest([offer$, brands$]).subscribe({
      next: ([offer, brands]) => {
        this.currentOffer = offer;
        this.brands = brands;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        this.errorService.navigateToErrorPage(err);
      }
    });
  }
}
