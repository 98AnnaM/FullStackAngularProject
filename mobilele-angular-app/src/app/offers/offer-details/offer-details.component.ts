import {Component, OnInit} from '@angular/core';
import {CommentAddComponent} from '../../comments/comment-add/comment-add.component';
import {CommentsListComponent} from '../../comments/comments-list/comments-list.component';
import {Offer} from '../../types/offer';
import {ActivatedRoute} from '@angular/router';
import {ApiService} from '../../api.service';
import {UserService} from '../../user/user.service';
import {TitleCasePipe} from '@angular/common';

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
offer = {} as Offer;

  constructor(private route: ActivatedRoute,
              private apiService: ApiService,
              private userService: UserService) {
  }

  ngOnInit(): void {
    // this.route.params.subscribe((data) => {
    //   console.log(data['offerId']);
    // });

    const id = this.route.snapshot.params['offerId'];
      this.apiService.getSingleOffer(id).subscribe((offer) => {
        this.offer = offer;
    });
  }

}
