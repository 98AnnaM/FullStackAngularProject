import {EngineEnum} from '../enums/engine-enum';
import {TransmissionEnum} from '../enums/transmission-enum';
import {CommentType} from './comment';

export interface Offer {
  id: number;
  imageUrl: string;
  year: number;
  brandName: string;
  modelName: string;
  mileage: number;
  price: number;
  engine: EngineEnum;
  transmission: TransmissionEnum;
  sellerFirstName: string;
  sellerLastName: string;
  sellerEmail: string;
  canDelete: boolean;
  description: string;
  comments: CommentType[];
  offerHighlight: string;
  sellerFullName: string;
}
