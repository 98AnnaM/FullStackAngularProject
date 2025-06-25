import {Component, OnInit} from '@angular/core';
import {BrandView} from '../../types/brandView';
import {BrandsService} from '../brands.service';

@Component({
  selector: 'app-brands-list',
  standalone: true,
  imports: [],
  templateUrl: './brands-list.component.html',
  styleUrl: './brands-list.component.css'
})
export class BrandsListComponent implements OnInit{
  brands: BrandView[] = [];
  isLoading: boolean = true;

  constructor(private brandsService: BrandsService) {}

  ngOnInit(): void {
    this.brandsService.getBrands().subscribe((brands) => {
      this.brands = brands;
      this.isLoading = false;
    });
  }

}
