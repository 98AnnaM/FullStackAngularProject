import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { OffersService } from '../offers.service';
import { OfferAddOrEdit } from '../../types/offerAddOrEdit';
import { OfferView } from '../../types/offerView';
import { FormErrorService } from '../../form-error.service';
import { CommonModule } from '@angular/common';
import {BrandsService} from '../../brands/brands.service';
import {BaseOfferForm} from '../base-offer-form';
import {ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-offer-add',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './offer-add.component.html',
  styleUrl: './offer-add.component.css'
})
export class OfferAddComponent extends BaseOfferForm implements OnInit {

  constructor(
    private offerService: OffersService,
    private brandsService: BrandsService,
    private formErrorService: FormErrorService,
    private router: Router
  ) {
    super();
  }

  ngOnInit(): void {
    this.brandsService.getBrands().subscribe(b => this.brands = b);
    }

  protected override saveOffer(offerDto: OfferAddOrEdit): void {
    this.offerService.createOffer(offerDto).subscribe({
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
