import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-pagination',
  standalone: true,
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.css'
})
export class PaginationComponent {
  @Input() page: number = 0;
  @Input() totalPages: number = 1;
  @Output() pageChange = new EventEmitter<number>();

  goToPage(newPage: number) {
    if (newPage >= 0 && newPage < this.totalPages) {
      this.pageChange.emit(newPage);
    }
  }

  get pageNumbers(): number[] {
    const pages: number[] = [];
    const maxDisplayed = 4;
    let start = Math.max(0, this.page - 3);
    let end = Math.min(this.totalPages, start + maxDisplayed);

    if (end - start < maxDisplayed) {
      start = Math.max(0, end - maxDisplayed);
    }

    for (let i = start; i < end; i++) {
      pages.push(i);
    }

    return pages;
  }
}
