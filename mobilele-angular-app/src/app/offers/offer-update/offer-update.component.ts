import {Component, OnInit} from '@angular/core';
import {BaseOfferForm} from '../base-offer-form';
import {OfferAddOrEdit} from '../../types/offerAddOrEdit';
import {OffersService} from '../offers.service';
import {BrandsService} from '../../brands/brands.service';
import {FormErrorService} from '../../form-error.service';
import {ActivatedRoute, Router} from '@angular/router';
import {OfferView} from '../../types/offerView';
import {LoaderComponent} from '../../shared/loader/loader.component';
import {combineLatest, finalize, Observable, of, tap} from 'rxjs';
import {BrandView} from '../../types/brandView';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TitleCasePipe} from '@angular/common';

@Component({
  selector: 'app-offer-update',
  standalone: true,
  imports: [
    LoaderComponent,
    FormsModule,
    ReactiveFormsModule,
    TitleCasePipe
  ],
  templateUrl: './offer-update.component.html',
  styleUrl: './offer-update.component.css'
})
export class OfferUpdateComponent extends BaseOfferForm implements OnInit {
  offerId: string = '';
  currentOffer: OfferView | undefined;
  isLoading = true;

  constructor(
    private offerService: OffersService,
    private brandsService: BrandsService,
    private formErrorService: FormErrorService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    super();
  }

  ngOnInit(): void {
    this.currentOffer = history.state.offer as OfferView | undefined;
    this.offerId = this.route.snapshot.params['offerId'];

    const offer$: Observable<OfferView> = this.currentOffer
      ? of(this.currentOffer!)
      : this.offerService.getSingleOffer(this.offerId);

    const brands$: Observable<BrandView[]> = this.brandsService.getBrands();

    this.loadFormData(offer$, brands$);
  }

  private loadFormData(offer$: Observable<OfferView>, brands$: Observable<BrandView[]>): void {
    combineLatest([offer$, brands$])
      .pipe(
        tap(([offer, brands]) => {
          this.currentOffer = offer;
          this.brands = brands;
          this.form.patchValue({...offer});
        }),
        finalize(() => this.isLoading = false)
      )
      .subscribe();
  }

  protected override saveOffer(offerDto: OfferAddOrEdit): void {
    this.offerService.updateOffer(this.offerId, offerDto).subscribe({
      next: (createdOffer: OfferView) => this.router.navigate([`/offers/${createdOffer.id}`]),
      error: err => {
        console.error(err);
        if (err.status === 400 && err.error?.errors) {
          this.formErrorService.mapBackendErrorsToForm(this.form, err.error.errors);
        }
      }
    });
  }
}
