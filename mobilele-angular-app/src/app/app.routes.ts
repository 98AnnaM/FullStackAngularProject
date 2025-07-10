import { Routes } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './user/login/login.component';
import {RegisterComponent} from './user/register/register.component';
import {OfferListComponent} from './offers/offer-list/offer-list.component';
import {OfferDetailsComponent} from './offers/offer-details/offer-details.component';
import {OfferAddComponent} from './offers/offer-add/offer-add.component';
import {ErrorPageComponent} from './errors/error/error-page.component';
import {BrandsListComponent} from './brands/brands-list/brands-list.component';
import {OfferUpdateComponent} from './offers/offer-update/offer-update.component';
import { LoaderComponent } from './shared/loader/loader.component';
import { OfferSearchComponent } from './offers/offer-search/offer-search.component';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},

  {path: 'users', children: [
      {path: 'login', component: LoginComponent},
      {path: 'register', component: RegisterComponent}
    ]},

  {path: 'offers', children: [
      {path: 'add', component: OfferAddComponent, canActivate: [AuthGuard] },
      {path: 'search', component: OfferSearchComponent},
      {path: '', component: OfferListComponent},
      {path: ':offerId', component: OfferDetailsComponent },
      {path: ':offerId/edit', component: OfferUpdateComponent, canActivate: [AuthGuard]}
    ]},

  {path: 'brands', component: BrandsListComponent},
  {path: 'loader', component: LoaderComponent},
  { path: 'error', component: ErrorPageComponent },
  { path: '**', redirectTo: '/error', pathMatch: 'full', }
];
