import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgClass, TitleCasePipe} from "@angular/common";
import {BrandView} from '../../types/brandView';
import {OfferAddOrEdit} from '../../types/offerAddOrEdit';
import {EngineEnum} from '../../enums/engine-enum';
import {TransmissionEnum} from '../../enums/transmission-enum';
import {wholeNumberBiggerThenValidator} from '../../validators/whole-number-bigger-then.validator';
import {OfferView} from '../../types/offerView';
import { backendValidator } from '../../validators/backend.validator';

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
export class OfferFormComponent implements OnInit, OnChanges {
  @Input() mode: 'create' | 'update' = 'create';
  @Input() brands: BrandView[] = [];
  @Input() offerData: OfferView | null = null;

  @Output() submitForm = new EventEmitter<OfferAddOrEdit>();

  engineTypes = Object.values(EngineEnum);
  transmissionTypes = Object.values(TransmissionEnum);

  form = new FormGroup({
    modelId: new FormControl<number | null>(null, [Validators.required,  backendValidator()]),
    price: new FormControl<number | null>(null, [Validators.required, wholeNumberBiggerThenValidator(1), backendValidator()]),
    year: new FormControl<number | null>(null, [Validators.required, wholeNumberBiggerThenValidator(1990), Validators.max(new Date().getFullYear()), backendValidator()]),
    description: new FormControl('', [Validators.required,  backendValidator()]),
    engine: new FormControl('', [Validators.required, backendValidator()]),
    transmission: new FormControl('', [Validators.required, backendValidator()]),
    imageUrl: new FormControl('', [Validators.required, backendValidator()]),
    mileage: new FormControl<number | null>(null, [Validators.required, wholeNumberBiggerThenValidator(0), Validators.max(999999), backendValidator()]),
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
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['offerData']?.currentValue) {
      this.patchForm(changes['offerData'].currentValue);
    }
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.submitForm.emit(this.form.value as OfferAddOrEdit);
  }
}
