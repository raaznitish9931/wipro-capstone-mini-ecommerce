import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { UserDashboardComponent } from './components/user-dashboard/user-dashboard.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { AddProductComponent } from './components/add-product/add-product.component';
import { ProfileComponent } from './components/profile/profile.component';
import { CartComponent } from './components/cart/cart.component';
import { PaymentComponent } from './components/payment/payment.component';
import { MyOrdersComponent } from './components/my-orders/my-orders.component';
import { WishlistComponent } from './components/wishlist/wishlist.component';

import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  { path: 'user-dashboard', component: UserDashboardComponent, canActivate: [authGuard] },

  { path: 'admin-dashboard', component: AdminDashboardComponent, canActivate: [authGuard] },
  { path: 'product-list', component: ProductListComponent, canActivate: [authGuard] },
  { path: 'add-product', component: AddProductComponent, canActivate: [authGuard] },
  { path: 'edit-product/:id', component: AddProductComponent, canActivate: [authGuard] },
  
  { path: 'profile', component: ProfileComponent, canActivate: [authGuard] },
  { path: 'cart', component: CartComponent, canActivate: [authGuard] },
  { path: 'payment', component: PaymentComponent, canActivate: [authGuard] },
  { path: 'my-orders', component: MyOrdersComponent, canActivate: [authGuard] },
  { path: 'wishlist', component: WishlistComponent, canActivate: [authGuard] }
];