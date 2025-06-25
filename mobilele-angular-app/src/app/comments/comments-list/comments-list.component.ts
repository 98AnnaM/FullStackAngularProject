import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CommentView} from '../../types/commentView';
import {CommentsService} from '../comments.service';

@Component({
  selector: 'app-comments-list',
  standalone: true,
  imports: [],
  templateUrl: './comments-list.component.html',
  styleUrl: './comments-list.component.css'
})
export class CommentsListComponent {
  @Input() comment!: CommentView;
  @Output() commentDeleted = new EventEmitter<number>();

  constructor(private commentsService: CommentsService) {
  }

  deleteComment(commentId: number) {
    this.commentsService.deleteComment(commentId).subscribe(() => {
      this.commentDeleted.emit(commentId);
    });
  }
}
