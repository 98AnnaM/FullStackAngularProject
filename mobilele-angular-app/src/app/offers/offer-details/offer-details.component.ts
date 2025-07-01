import { Component, OnInit } from '@angular/core';
import { CommentAddComponent } from '../../comments/comment-add/comment-add.component';
import { CommentsListComponent } from '../../comments/comments-list/comments-list.component';
import { OfferView } from '../../types/offerView';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { UserService } from '../../user/user.service';
import { TitleCasePipe } from '@angular/common';
import { CommentView } from '../../types/commentView';
import { OffersService } from '../offers.service';
import { CommentsService } from '../../comments/comments.service';
import { LoaderComponent } from '../../shared/loader/loader.component';

@Component({
  selector: 'app-offer-details',
  standalone: true,
  imports: [
    CommentAddComponent,
    CommentsListComponent,
    TitleCasePipe,
    RouterLink,
    LoaderComponent
  ],
  templateUrl: './offer-details.component.html',
  styleUrl: './offer-details.component.css'
})
export class OfferDetailsComponent implements OnInit {
  offer = {} as OfferView;
  offerId: string = '';
  isLoading: boolean = true;

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
    this.offerService.getSingleOffer(this.offerId).subscribe({
      next: (offer) => {
        this.offer = offer;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        const status = err?.status;
        this.router.navigate(['/error', status || '500']);
      }
    });
  }

  reFetchCommentsList(): void {
    this.commentsService.getComments(this.offerId).subscribe({
      next: (comments: CommentView[]) => {
        this.offer.comments = comments;
      },
      error: (err) => {
        const status = err?.status;
        this.router.navigate(['/error', status || '500']);
      }
    });
  }

  deleteOffer(): void {
    this.isLoading = true;

    this.offerService.deleteOffer(this.offerId).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/offers']).catch(err => {
          console.error('Navigation failed:', err);
        });
      },
      error: (err) => {
        this.isLoading = false;
        const status = err?.status;
        this.router.navigate(['/error', status || '500']);
      }
    });
  }

  goToEdit(offer: OfferView) {
    this.router.navigate(['/offers', offer.id, 'edit'], {state: {offer}});
  }
}
