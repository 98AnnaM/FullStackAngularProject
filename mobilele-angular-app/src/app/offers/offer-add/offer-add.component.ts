import {Component, OnInit} from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { OffersService } from '../offers.service';
import { OfferAdd } from '../../types/offerAdd';
import { OfferView } from '../../types/offerView';
import { FormErrorService } from '../../form-error.service';
import { CommonModule } from '@angular/common';
import {TransmissionEnum} from '../../enums/transmission-enum';
import {EngineEnum} from '../../enums/engine-enum';
import {BrandView} from '../../types/brandView';
import {BrandsService} from '../../brands/brands.service';
import {wholeNumberBiggerThenValidator} from '../../validators/whole-number-bigger-then.validator';

@Component({
  selector: 'app-offer-add',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './offer-add.component.html',
  styleUrl: './offer-add.component.css'
})
export class OfferAddComponent implements OnInit {
  brands: BrandView[] = [];
  engineTypes = Object.values(EngineEnum);
  transmissionTypes = Object.values(TransmissionEnum);

  form = new FormGroup({
    modelId: new FormControl(null, [Validators.required]),
    price: new FormControl(null, [Validators.required, wholeNumberBiggerThenValidator(1)]),
    year: new FormControl(null, [Validators.required, wholeNumberBiggerThenValidator(1990), Validators.max(new Date().getFullYear())]),
    description: new FormControl('', [Validators.required]),
    engine: new FormControl('', [Validators.required]),
    transmission: new FormControl('', [Validators.required]),
    imageUrl: new FormControl('', [Validators.required]),
    mileage: new FormControl('', [Validators.required, wholeNumberBiggerThenValidator(0), Validators.max(999999)]),
  });

  constructor(
    private offerService: OffersService,
    private brandsService: BrandsService,
    private formErrorService: FormErrorService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.brandsService.getBrands().subscribe({
      next: (data) => this.brands = data,
      error: (err) => console.error('Failed to load brands', err)
    });
    }

  addOffer(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched(); // Show validation feedback if needed
      return;
    }

    const v = this.form.value;

    const offerAdd: OfferAdd = {
      modelId: Number(v.modelId),
      price: Number(v.price),
      year: Number(v.year),
      description: v.description!,
      engine: v.engine as EngineEnum,
      transmission: v.transmission as TransmissionEnum,
      imageUrl: v.imageUrl!,
      mileage: Number(v.mileage),
    };

    this.offerService.createOffer(offerAdd).subscribe({
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
