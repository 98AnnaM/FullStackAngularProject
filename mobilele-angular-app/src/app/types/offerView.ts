import {EngineEnum} from '../enums/engine-enum';
import {TransmissionEnum} from '../enums/transmission-enum';

export interface OfferView {
  id: number;
  imageUrl: string;
  year: number;
  brandName: string;
  modelName: string;
  modelId: number;
  mileage: number;
  price: number;
  engine: EngineEnum;
  transmission: TransmissionEnum;
  sellerFirstName: string;
  sellerLastName: string;
  sellerEmail: string;
  canDelete: boolean;
  canUpdate: boolean;
  description: string;
  offerHighlight: string;
  sellerFullName: string;
}
