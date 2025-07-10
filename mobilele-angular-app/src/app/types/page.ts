export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number; // current page index
  size: number;   // size of the page
}
