import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BrandView} from '../types/brandView';
import { delay } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BrandsService {

  constructor(private http: HttpClient) { }

  getBrands() {
    return this.http.get<BrandView[]>('http://localhost:8080/brands/all').pipe(delay(2000));
  }
}
