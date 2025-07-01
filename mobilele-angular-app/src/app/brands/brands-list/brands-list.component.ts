import { Component, OnInit } from '@angular/core';
import { BrandView } from '../../types/brandView';
import { BrandsService } from '../brands.service';
import { Router } from '@angular/router';
import { LoaderComponent } from '../../shared/loader/loader.component'; // <-- import Router

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
    private router: Router // <-- inject Router
  ) {}

  ngOnInit(): void {
    this.brandsService.getBrands().subscribe({
      next: (brands) => {
        this.brands = brands;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        const status = err?.status;
        this.router.navigate(['/error', status || '500']);
      }
    });
  }
}
