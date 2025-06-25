import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CommentAdd} from '../types/commentAdd';
import {CommentView} from '../types/commentView';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  constructor(private http: HttpClient) { }

  createComment(commentAdd: CommentAdd, offerId: string) {
    return this.http.post<CommentView>(`http://localhost:8080/offers/${offerId}/comments`, commentAdd);
  }

  getComments(offerId: string) {
    return this.http.get<CommentView[]>(`http://localhost:8080/offers/${offerId}/comments`);
  }

  deleteComment(commentId: number): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/comments/${commentId}`);
  }
}
