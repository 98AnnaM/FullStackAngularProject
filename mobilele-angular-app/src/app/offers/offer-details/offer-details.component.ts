import {Component, OnInit} from '@angular/core';
import {CommentAddComponent} from '../../comments/comment-add/comment-add.component';
import {CommentsListComponent} from '../../comments/comments-list/comments-list.component';
import {OfferView} from '../../types/offerView';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../../user/user.service';
import {TitleCasePipe} from '@angular/common';
import {CommentView} from '../../types/commentView';
import {OffersService} from '../offers.service';
import {CommentsService} from '../../comments/comments.service';

@Component({
  selector: 'app-offer-details',
  standalone: true,
  imports: [
    CommentAddComponent,
    CommentsListComponent,
    TitleCasePipe
  ],
  templateUrl: './offer-details.component.html',
  styleUrl: './offer-details.component.css'
})
export class OfferDetailsComponent  implements OnInit {
offer = {} as OfferView;

  constructor(private route: ActivatedRoute,
              private offerService: OffersService,
              private commentsService: CommentsService,
              private userService: UserService) {
  }

  get isLoggedIn(): boolean {
    return this.userService.isLogged;
  }

  ngOnInit(): void {
    // this.route.params.subscribe((data) => {
    //   console.log(data['offerId']);
    // });

    const id = this.route.snapshot.params['offerId'];
      this.offerService.getSingleOffer(id).subscribe((offer) => {
        this.offer = offer;
    });
  }

  reFetchCommentsList(): void {
    const offerId = this.route.snapshot.params['offerId'];

    this.commentsService.getComments(offerId).subscribe((comments: CommentView[]) => {
      this.offer.comments = comments;
    });
  }
}
