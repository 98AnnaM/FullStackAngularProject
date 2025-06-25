import {CategoryEnum} from '../enums/category-enum';

export interface BrandView {
  name: string;
  models: ModelView[];
}

export interface ModelView {
  id: number;
  name: string;
  category: CategoryEnum;
  imageUrl: string;
  startYear: number;
  endYear: number;
}
