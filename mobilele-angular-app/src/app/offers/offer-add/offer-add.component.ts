import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { OffersService } from '../offers.service';
import { OfferAddOrEdit } from '../../types/offerAddOrEdit';
import { OfferView } from '../../types/offerView';
import { FormErrorService } from '../../form-error.service';
import { CommonModule } from '@angular/common';
import { BrandsService } from '../../brands/brands.service';
import { ReactiveFormsModule } from '@angular/forms';
import { OfferFormComponent } from '../offer-form/offer-form.component';
import { BrandView } from '../../types/brandView';
import { LoaderComponent } from '../../shared/loader/loader.component';

@Component({
  selector: 'app-offer-add',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, OfferFormComponent, LoaderComponent],
  templateUrl: './offer-add.component.html',
  styleUrls: ['./offer-add.component.css']  // ⬅️ "styleUrls", not "styleUrl"
})
export class OfferAddComponent implements OnInit {

  brands: BrandView[] = [];
  isLoading: boolean = true;

  @ViewChild(OfferFormComponent)
  offerFormComponent!: OfferFormComponent;

  constructor(
    private offerService: OffersService,
    private brandsService: BrandsService,
    private formErrorService: FormErrorService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.brandsService.getBrands().subscribe({
      next: (brands) => {
        this.brands = brands;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        const status = err?.status;
        this.router.navigate(['/error', status || '500']);
      }
    });
  }

  createOffer(offerDto: OfferAddOrEdit): void {
    this.isLoading = true;
    this.offerService.createOffer(offerDto).subscribe({
      next: (createdOffer: OfferView) => {
        this.isLoading = false;
        this.router.navigate([`/offers/${createdOffer.id}`]);
      },
      error: err => {
        this.isLoading = false;
        console.error(err);
        if (err.status === 400 && err.error?.errors) {
          this.formErrorService.mapBackendErrorsToForm(
            this.offerFormComponent.form,
            err.error.errors
          );
        } else {
          const status = err?.status;
          this.router.navigate(['/error', status || '500']);
        }
      }
    });
  }
}
