import { Component, OnInit } from '@angular/core';
import { CommentAddComponent } from '../../comments/comment-add/comment-add.component';
import { CommentComponent } from '../../comments/comment/comment.component';
import { OfferView } from '../../types/offerView';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { OffersService } from '../offers.service';
import { LoaderComponent } from '../../shared/loader/loader.component';
import { ErrorService } from '../../errors/error.service';
import { TitleCasePipe } from '@angular/common';
import { ConfirmationModalComponent } from '../../shared/confirmation-modal/confirmation-modal.component';
import { CommentsListComponent } from '../../comments/comments-list/comments-list.component';

@Component({
  selector: 'app-offer-details',
  standalone: true,
  imports: [
    CommentAddComponent,
    CommentComponent,
    TitleCasePipe,
    RouterLink,
    LoaderComponent,
    ConfirmationModalComponent,
    CommentsListComponent
  ],
  templateUrl: './offer-details.component.html',
  styleUrls: ['./offer-details.component.css']  // ✅ corrected
})
export class OfferDetailsComponent implements OnInit {
  offer: OfferView = {} as OfferView;
  offerId: string = '';
  isLoading: boolean = true;
  showConfirmModal: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private errorService: ErrorService,
    private offerService: OffersService  // ✅ removed trailing comma
  ) {}

  ngOnInit(): void {
    this.offerId = this.route.snapshot.params['offerId'];
    this.fetchOfferDetails();
  }

  deleteOffer(): void {
    this.isLoading = true;

    this.offerService.deleteOffer(this.offerId).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/offers']);
      },
      error: (err) => {
        this.isLoading = false;
        this.errorService.navigateToErrorPage(err);
      }
    });
  }

  goToEdit(offer: OfferView): void {
    this.router.navigate(['/offers', offer.id, 'edit'], { state: { offer } });
  }

  openDeleteModal(): void {
    this.showConfirmModal = true;
  }

  handleCancel(): void {
    this.showConfirmModal = false;
  }

  private fetchOfferDetails(): void {
    this.offerService.getSingleOffer(this.offerId).subscribe({
      next: (offer) => {
        this.offer = offer;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        this.errorService.navigateToErrorPage(err);
      }
    });
  }
}
