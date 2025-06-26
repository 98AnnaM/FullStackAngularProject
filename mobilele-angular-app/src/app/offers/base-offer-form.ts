import {BrandView} from '../types/brandView';
import {EngineEnum} from '../enums/engine-enum';
import {TransmissionEnum} from '../enums/transmission-enum';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {wholeNumberBiggerThenValidator} from '../validators/whole-number-bigger-then.validator';
import {OfferAddOrEdit} from '../types/offerAddOrEdit';

export abstract class BaseOfferForm {
  brands: BrandView[] = [];
  engineTypes = Object.values(EngineEnum);
  transmissionTypes = Object.values(TransmissionEnum);

  form = new FormGroup({
    modelId: new FormControl<number | null>(null, [Validators.required]),
    price: new FormControl<number | null>(null, [Validators.required, wholeNumberBiggerThenValidator(1)]),
    year: new FormControl<number | null>(null, [Validators.required, wholeNumberBiggerThenValidator(1990), Validators.max(new Date().getFullYear())]),
    description: new FormControl('', [Validators.required]),
    engine: new FormControl('', [Validators.required]),
    transmission: new FormControl('', [Validators.required]),
    imageUrl: new FormControl('', [Validators.required]),
    mileage: new FormControl<number | null>(null, [Validators.required, wholeNumberBiggerThenValidator(0), Validators.max(999999)]),
  });

  protected toDto(): OfferAddOrEdit {
    const v = this.form.value;
    return {
      modelId:      Number(v.modelId),
      price:        Number(v.price),
      year:         Number(v.year),
      description:  v.description!,
      engine:       v.engine as EngineEnum,
      transmission: v.transmission as TransmissionEnum,
      imageUrl:     v.imageUrl!,
      mileage:      Number(v.mileage)
    };
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saveOffer(this.toDto());
  }

  protected abstract saveOffer(offerDto: OfferAddOrEdit): void;

}
