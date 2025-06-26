import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {OfferView} from '../types/offerView';
import {OfferAddOrEdit} from '../types/offerAddOrEdit';
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
    console.log('Get offer call is made');
    return this.http.get<OfferView>(`http://localhost:8080/offers/${id}`);
  }

  createOffer(offerAdd: OfferAddOrEdit) {
    return this.http.post<OfferView>('http://localhost:8080/offers/add', offerAdd);
  }

  updateOffer(id: string, offerUpdate: OfferAddOrEdit) {
    return this.http.put<OfferView>(`http://localhost:8080/offers/edit/${id}`, offerUpdate);
  }

  deleteOffer(id: string): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/offers/${id}`);
  }
}
