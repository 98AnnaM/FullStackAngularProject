import {EngineEnum} from '../enums/engine-enum';
import {TransmissionEnum} from '../enums/transmission-enum';

export interface OfferAdd {
  modelId: number;
  price: number;
  year: number;
  description: string;
  engine: EngineEnum;
  transmission: TransmissionEnum;
  imageUrl: string;
  mileage: number;
}
