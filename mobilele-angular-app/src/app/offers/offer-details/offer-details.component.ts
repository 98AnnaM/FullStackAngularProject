import { Component } from '@angular/core';
import {CommentAddComponent} from '../../comments/comment-add/comment-add.component';
import {CommentsListComponent} from '../../comments/comments-list/comments-list.component';

@Component({
  selector: 'app-offer-details',
  standalone: true,
  imports: [
    CommentAddComponent,
    CommentsListComponent
  ],
  templateUrl: './offer-details.component.html',
  styleUrl: './offer-details.component.css'
})
export class OfferDetailsComponent {

}
