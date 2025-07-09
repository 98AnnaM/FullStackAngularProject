import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {OfferView} from '../types/offerView';
import {OfferAddOrEdit} from '../types/offerAddOrEdit';
import { delay, dematerialize, materialize, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OffersService {

  constructor(private http: HttpClient) { }

  getOffers() {
    return this.http.get<OfferView[]>('/api/offers/all')
      .pipe(delay(2000));
  }

  getSingleOffer(id: string) {
    console.log('Get offer call is made');
    return this.http.get<OfferView>(`/api/offers/${id}`)
      .pipe(delay(2000));
  }

  createOffer(offerAdd: OfferAddOrEdit) {
    return this.http.post<OfferView>('/api/offers/add', offerAdd).pipe(
        materialize(),
        delay(2000),
        dematerialize()
      );
  }

  updateOffer(id: string, offerUpdate: OfferAddOrEdit) {
    return this.http.put<OfferView>(`/api/offers/edit/${id}`, offerUpdate).pipe(
      materialize(),
      delay(2000),
      dematerialize()
    );
  }

  deleteOffer(id: string): Observable<void> {
    return this.http.delete<void>(`/api/offers/${id}`)
      .pipe(delay(2000));
  }
}
