import { Component, OnInit } from '@angular/core';
import { EngineEnum } from '../../enums/engine-enum';
import { TransmissionEnum } from '../../enums/transmission-enum';
import { BrandView } from '../../types/brandView';
import { OffersService } from '../offers.service';
import { BrandsService } from '../../brands/brands.service';
import { ErrorService } from '../../errors/error.service';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { wholeNumberBiggerThenValidator } from '../../validators/whole-number-bigger-then.validator';
import { TitleCasePipe } from '@angular/common';
import { LoaderComponent } from '../../shared/loader/loader.component';
import { OfferSearch } from '../../types/offerSearch';
import { OfferView } from '../../types/offerView';
import { OfferListComponent } from '../offer-list/offer-list.component';
import { OfferCardComponent } from '../offer-card/offer-card.component';
import { toHttpParams } from '../../utils/toHttpParams';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-offer-search',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    TitleCasePipe,
    LoaderComponent,
    OfferListComponent,
    OfferCardComponent
  ],
  templateUrl: './offer-search.component.html',
  styleUrl: './offer-search.component.css'
})
export class OfferSearchComponent implements OnInit{

  engineTypes = Object.values(EngineEnum);
  transmissionTypes = Object.values(TransmissionEnum);
  brands: BrandView[] = [];
  searchResults: OfferView[] = [];
  isLoading: boolean = true;
  isSearching: boolean = false;
  searchPerformed: boolean = false;

  form = new FormGroup({
    modelId: new FormControl<number | null>(null, []),
    minPrice: new FormControl<number | null>(null, [
      wholeNumberBiggerThenValidator(1)
    ]),
    maxPrice: new FormControl<number | null>(null, [
      wholeNumberBiggerThenValidator(1)
    ]),
    engine: new FormControl('', []),
    transmission: new FormControl('', []),
  });

  constructor(
    private offerService: OffersService,
    private brandsService: BrandsService,
    private errorService: ErrorService,
  ) {}

  ngOnInit(): void {
    this.brandsService.getBrands().subscribe({
      next: (brands) => {
        this.brands = brands;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        this.errorService.navigateToErrorPage(err);
      }
    });
  }


  onSubmit() {
    if (this.form.invalid) {
      return;
    }

    this.isSearching = true;

    const offerSearch = this.form.value as OfferSearch;
    const paramsObj = toHttpParams(offerSearch);
    const params = new HttpParams({fromObject: paramsObj});

    this.offerService.searchOffer(params)
      .subscribe({
        next: (offers: OfferView[]) => {
          this.isSearching = false;
          this.searchPerformed = true;
          this.searchResults = offers;
        },
        error: err => {
          this.errorService.navigateToErrorPage(err);
          this.isSearching = false;
        }
      });

  }
}
