import {Component, Input} from '@angular/core';
import {CommentType} from '../../types/comment';

@Component({
  selector: 'app-comments-list',
  standalone: true,
  imports: [],
  templateUrl: './comments-list.component.html',
  styleUrl: './comments-list.component.css'
})
export class CommentsListComponent {
  @Input() comment!: CommentType;

}
