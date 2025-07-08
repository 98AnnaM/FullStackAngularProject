import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgClass, TitleCasePipe} from "@angular/common";
import {BrandView} from '../../types/brandView';
import {OfferAddOrEdit} from '../../types/offerAddOrEdit';
import {EngineEnum} from '../../enums/engine-enum';
import {TransmissionEnum} from '../../enums/transmission-enum';
import {wholeNumberBiggerThenValidator} from '../../validators/whole-number-bigger-then.validator';
import { BackendValidationMap } from '../../types/backendValidationMap';

@Component({
  selector: 'app-offer-form',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    TitleCasePipe,
    NgClass
  ],
  templateUrl: './offer-form.component.html',
  styleUrl: './offer-form.component.css'
})
export class OfferFormComponent implements OnInit {
  @Input() mode: 'create' | 'update' = 'create';
  @Input() brands: BrandView[] = [];
  @Input() backendErrorsMap: BackendValidationMap = {};
  @Input() offerData: OfferAddOrEdit | null = null;

  @Output() submitForm = new EventEmitter<OfferAddOrEdit>();

  engineTypes = Object.values(EngineEnum);
  transmissionTypes = Object.values(TransmissionEnum);

  form = new FormGroup({
    modelId: new FormControl<number | null>(null, [
      Validators.required
    ]),
    price: new FormControl<number | null>(null, [
      Validators.required,
      wholeNumberBiggerThenValidator(1)
    ]),
    year: new FormControl<number | null>(null, [
      Validators.required,
      wholeNumberBiggerThenValidator(1990),
      Validators.max(new Date().getFullYear())
    ]),
    description: new FormControl('', [
      Validators.required
    ]),
    engine: new FormControl('', [
      Validators.required]),
    transmission: new FormControl('', [
      Validators.required
    ]),
    imageUrl: new FormControl('', [
      Validators.required
    ]),
    mileage: new FormControl<number | null>(null, [
      Validators.required,
      wholeNumberBiggerThenValidator(0),
      Validators.max(999999)
    ]),
  });

  protected patchForm(dto: OfferAddOrEdit): void {
    this.form.patchValue({
      modelId:      Number(dto.modelId),
      price:        Number(dto.price),
      year:         Number(dto.year),
      description:  dto.description!,
      engine:       dto.engine as EngineEnum,
      transmission: dto.transmission as TransmissionEnum,
      imageUrl:     dto.imageUrl!,
      mileage:      Number(dto.mileage)
    });
  }

  ngOnInit(): void {
    if (this.offerData) {
      this.patchForm(this.offerData);
    }

    Object.keys(this.form.controls).forEach(fieldName => {
      this.form.get(fieldName)?.valueChanges.subscribe(() => {
        if (this.backendErrorsMap[fieldName]) {
          delete this.backendErrorsMap[fieldName];
        }
      });
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.submitForm.emit(this.form.value as OfferAddOrEdit);
  }

  hasBackendErrors(): boolean {
    return Object.keys(this.backendErrorsMap).length > 0;
  }
}
