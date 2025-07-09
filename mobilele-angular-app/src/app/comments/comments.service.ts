import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CommentAdd} from '../types/commentAdd';
import {CommentView} from '../types/commentView';
import { delay, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  constructor(private http: HttpClient) { }

  createComment(commentAdd: CommentAdd, offerId: string) {
    return this.http.post<CommentView>(`/api/offers/${offerId}/comments`, commentAdd)
      .pipe(delay(2000));
  }

  getComments(offerId: string) {
    return this.http.get<CommentView[]>(`/api/offers/${offerId}/comments`)
      .pipe(delay(2000));
  }

  deleteComment(commentId: number): Observable<void> {
    return this.http.delete<void>(`/api/comments/${commentId}`)
      .pipe(delay(2000));
  }
}
