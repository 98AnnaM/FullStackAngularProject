import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {OfferView} from '../types/offerView';
import {OfferAdd} from '../types/offerAdd';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OffersService {

  constructor(private http: HttpClient) { }

  getOffers() {
    return this.http.get<OfferView[]>('http://localhost:8080/offers/all');
  }

  getSingleOffer(id: string) {
    return this.http.get<OfferView>(`http://localhost:8080/offers/${id}`);
  }

  createOffer(offerAdd: OfferAdd) {
    return this.http.post<OfferView>('http://localhost:8080/offers/add', offerAdd);
  }

  deleteOffer(id: string): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/offers/${id}`);
  }
}
