import {Component, EventEmitter, Output} from '@angular/core';
import {UserService} from '../../user/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import {FormsModule, NgForm} from '@angular/forms';
import {CommentAdd} from '../../types/commentAdd';
import {CommentsService} from '../comments.service';
import { LoaderComponent } from '../../shared/loader/loader.component';

@Component({
  selector: 'app-comment-add',
  standalone: true,
  imports: [FormsModule, LoaderComponent],
  templateUrl: './comment-add.component.html',
  styleUrls: ['./comment-add.component.css']  // fixed typo styleUrl -> styleUrls
})
export class CommentAddComponent {
  @Output() newCommentAdded = new EventEmitter<void>();

  isLoading = false;  // loader flag

  constructor(
    private userService: UserService,
    private commentsService: CommentsService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  get username(): string {
    const user = this.userService.user;
    if (!user) return '';
    return `${user.firstName} ${user.lastName} (${user.email})`;
  }

  addComment(form: NgForm) {
    if (form.invalid) return;

    this.isLoading = true;  // start loading

    const commentAdd: CommentAdd = form.value;
    const offerId = this.route.snapshot.params['offerId'];

    this.commentsService.createComment(commentAdd, offerId).subscribe({
      next: () => {
        this.newCommentAdded.emit();
        form.resetForm();
        this.isLoading = false;  // stop loading on success
      },
      error: (err) => {
        this.isLoading = false;  // stop loading on error
        const status = err?.status;
        this.router.navigate(['/error', status || '500']);
      }
    });
  }
}
