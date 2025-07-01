import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommentView } from '../../types/commentView';
import { CommentsService } from '../comments.service';
import { Router } from '@angular/router';
import { LoaderComponent } from '../../shared/loader/loader.component';

@Component({
  selector: 'app-comments-list',
  standalone: true,
  imports: [
    LoaderComponent
  ],
  templateUrl: './comments-list.component.html',
  styleUrl: './comments-list.component.css'
})
export class CommentsListComponent {
  @Input() comment!: CommentView;
  @Output() commentDeleted = new EventEmitter<number>();

  isLoading: boolean = false;

  constructor(private commentsService: CommentsService,
              private router: Router) {
  }

  deleteComment(commentId: number) {
    this.isLoading = true;
    this.commentsService.deleteComment(commentId).subscribe({
      next: () => {
        this.isLoading = false;
        this.commentDeleted.emit(commentId);
      },
      error: (err) => {
        this.isLoading = false;
        const status = err?.status;
        this.router.navigate(['/error', status || '500']);
      }
    });
  }
}
