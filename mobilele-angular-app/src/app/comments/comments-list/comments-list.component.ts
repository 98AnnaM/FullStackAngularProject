import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommentView } from '../../types/commentView';
import { CommentsService } from '../comments.service';
import { LoaderComponent } from '../../shared/loader/loader.component';
import { ErrorService } from '../../errors/error.service';
import { ConfirmationModalComponent } from '../../shared/confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'app-comments-list',
  standalone: true,
  imports: [
    LoaderComponent,
    ConfirmationModalComponent
  ],
  templateUrl: './comments-list.component.html',
  styleUrl: './comments-list.component.css'
})
export class CommentsListComponent {
  @Input() comment!: CommentView;
  @Output() commentDeleted = new EventEmitter<number>();

  isLoading: boolean = false;
  showConfirmModal: boolean = false;

  constructor(private commentsService: CommentsService,
              private errorService: ErrorService) {
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
        this.errorService.navigateToErrorPage(err);
      }
    });
  }

  openDeleteModal() {
    this.showConfirmModal = true;
  }

  handleCancel() {
    this.showConfirmModal = false;
  }
}
