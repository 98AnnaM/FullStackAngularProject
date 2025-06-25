import {Component, OnInit} from '@angular/core';
import {CommentAddComponent} from '../../comments/comment-add/comment-add.component';
import {CommentsListComponent} from '../../comments/comments-list/comments-list.component';
import {OfferView} from '../../types/offerView';
import {ActivatedRoute, Router} from '@angular/router';
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
offerId: string = '';
isDeleting: boolean = false;

  constructor(private route: ActivatedRoute,
              private router: Router,
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

    this.offerId = this.route.snapshot.params['offerId'];
      this.offerService.getSingleOffer(this.offerId).subscribe((offer) => {
        this.offer = offer;
    });
  }

  reFetchCommentsList(): void {
    this.commentsService.getComments(this.offerId).subscribe((comments: CommentView[]) => {
      this.offer.comments = comments;
    });
  }

  deleteOffer(): void {
    if (this.isDeleting) return; // prevent double deletion

    this.isDeleting = true;

    this.offerService.deleteOffer(this.offerId).subscribe({
      next: () => {
        this.isDeleting = false;
        this.router.navigate(['/offers']).catch(err => {
          console.error('Navigation failed:', err);
        });
      },
      error: (err) => {
        this.isDeleting = false;
        console.error('Failed to delete offer:', err);
      }
    });
  }
}
