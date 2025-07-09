import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { OfferView } from '../../types/offerView';

@Component({
  selector: 'app-offer-card',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './offer-card.component.html',
  styleUrl: './offer-card.component.css'
})
export class OfferCardComponent {
  @Input() offer!: OfferView;
}
