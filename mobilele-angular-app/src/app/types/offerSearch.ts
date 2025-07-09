import { EngineEnum } from '../enums/engine-enum';
import { TransmissionEnum } from '../enums/transmission-enum';

export interface OfferSearch {
  modelId: number;
  minPrice: number;
  maxPrice: number;
  engine: EngineEnum;
  transmission: TransmissionEnum;
}
