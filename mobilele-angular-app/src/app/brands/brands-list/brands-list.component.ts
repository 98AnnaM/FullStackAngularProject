import { Component, OnInit } from '@angular/core';
import { LoaderComponent } from '../../shared/loader/loader.component';
import { BrandView } from '../../types/brandView';
import { BrandsService } from '../brands.service';
import { ErrorService } from '../../errors/error.service';

@Component({
  selector: 'app-brands-list',
  standalone: true,
  imports: [
    LoaderComponent
  ],
  templateUrl: './brands-list.component.html',
  styleUrl: './brands-list.component.css'
})
export class BrandsListComponent implements OnInit {
  brands: BrandView[] = [];
  isLoading: boolean = true;

  constructor(
    private brandsService: BrandsService,
    private errorService: ErrorService
  ) {
  }

  ngOnInit(): void {
    this.brandsService.getBrands().subscribe({
      next: (brands) => {
        this.brands = brands;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        this.errorService.navigateToErrorPage(err);
      }
    });
  }
}
