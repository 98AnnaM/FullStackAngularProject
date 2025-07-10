import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import {CommentAdd} from '../types/commentAdd';
import {CommentView} from '../types/commentView';
import { delay, Observable } from 'rxjs';
import { Page } from '../types/page';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  constructor(private http: HttpClient) { }

  createComment(commentAdd: CommentAdd, offerId: string) {
    return this.http.post<CommentView>(`/api/offers/${offerId}/comments`, commentAdd)
      .pipe(delay(2000));
  }

  deleteComment(commentId: number): Observable<void> {
    return this.http.delete<void>(`/api/comments/${commentId}`)
      .pipe(delay(2000));
  }

  getComments(offerId: string, page: number = 0, size: number = 5): Observable<Page<CommentView>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<Page<CommentView>>(`/api/offers/${offerId}/comments`, { params })
      .pipe(delay(2000));
  }
}
