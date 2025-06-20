import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Offer} from './types/offer';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  getOffers() {
    let url = `http://localhost:8080/offers/all`;
    return this.http.get<Offer[]>(url);
  }

  getSingleOffer(id: string) {
    return this.http.get<Offer>(`http://localhost:8080/offers/${id}`);
  }
}
