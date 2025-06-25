import {Component, EventEmitter, Output} from '@angular/core';
import {UserService} from '../../user/user.service';
import {ActivatedRoute} from '@angular/router';
import {FormsModule, NgForm} from '@angular/forms';
import {CommentAdd} from '../../types/commentAdd';
import {CommentsService} from '../comments.service';

@Component({
  selector: 'app-comment-add',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './comment-add.component.html',
  styleUrl: './comment-add.component.css'
})
export class CommentAddComponent {
  @Output()newCommentAdded = new EventEmitter<void>();

  constructor(
    private userService: UserService,
    private commentsService: CommentsService,
    private route: ActivatedRoute
  ) {}

  get username(): string {
    const user = this.userService.user;
    if (!user) return '';
    return `${user.firstName} ${user.lastName} (${user.email})`;
  }

  addComment(form: NgForm) {
    if (form.invalid) return;

    const commentAdd: CommentAdd = form.value;
    const offerId = this.route.snapshot.params['offerId'];

    this.commentsService.createComment(commentAdd, offerId).subscribe(() => {
      this.newCommentAdded.emit(); // just trigger parent to re-fetch
      form.resetForm();
    });
  }

}
