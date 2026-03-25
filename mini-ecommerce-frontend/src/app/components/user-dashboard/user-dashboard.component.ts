




import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { AuthService } from '../../services/auth.service';
import { Product } from '../../models/product';
import { User } from '../../models/user';
import { CartService } from '../../services/cart.service';
import { WishlistService } from '../../services/wishlist.service';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-user-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, NavbarComponent],
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {

  products: Product[] = [];
  filteredProducts: Product[] = [];

  // Track toast notification state
  toastMessage: string = '';
  showToast: boolean = false;
  toastTimer: any;

  // Track button states per product
  addingStatus: { [key: number]: string } = {};

  searchText = '';
  selectedCategory = '';
  selectedPrice = '';
  categories: string[] = [
    'Electronics',
    'Fashion',
    'Beauty & Personal Care',
    'Home & Furniture',
    'Stationery',
    'Grocery'
  ];
  user: User | null = null;
  isDropdownOpen = false;

  constructor(
    private productService: ProductService,
    private authService: AuthService,
    private router: Router,
    private cartService: CartService,
    public wishlistService: WishlistService
  ) { }

  ngOnInit(): void {
    this.user = this.authService.getLoggedInUser();

    if (!this.user) {
      this.router.navigate(['/login']);
      return;
    }

    this.loadProducts();
  }

  get userRole(): string {
    if (!this.user || !this.user.role) return 'User';
    return this.user.role.toUpperCase() === 'ADMIN' ? 'Admin' : 'User';
  }

  loading = true;
  errorMessage = '';

  loadProducts() {
    this.loading = true;
    this.errorMessage = '';
    this.productService.getAllProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.filteredProducts = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Microservice Error:', err);
        this.loading = false;
        this.errorMessage = err.error?.message || 'Unable to load services. Please ensure microservices are running.';
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  toggleDropdown(): void {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  applyFilters() {
    this.filteredProducts = this.products.filter(product => {

      const searchTextLower = this.searchText?.trim().toLowerCase() || '';
      const matchSearch =
        searchTextLower.length < 2 ||
        (product.name?.toLowerCase().includes(searchTextLower) ?? false) ||
        (product.brand?.toLowerCase().includes(searchTextLower) ?? false);

      const matchCategory =
        !this.selectedCategory ||
        product.category?.toLowerCase() === this.selectedCategory.toLowerCase();

      const matchPrice =
        !this.selectedPrice ||
        this.checkPrice(product.price);

      return matchSearch && matchCategory && matchPrice;
    });
  }

  checkPrice(price: any): boolean {
    const p = Number(price);
    switch (this.selectedPrice) {
      case 'below1000': return p <= 1000;
      case 'below5000': return p <= 5000;
      case 'below10000': return p <= 10000;
      case 'below50000': return p <= 50000;
      default: return true;
    }
  }

  resetFilters() {
    this.searchText = '';
    this.selectedCategory = '';
    this.selectedPrice = '';
    this.filteredProducts = this.products;
  }

  addToCart(product: Product) {
    this.cartService.addToCart(product);

    // Animation & UI feedback
    const id = product.id as number;
    this.addingStatus[id] = 'ADDED';

    // Show global toast
    this.toastMessage = `${product.name} added to cart!`;
    this.showToast = true;

    if (this.toastTimer) clearTimeout(this.toastTimer);
    this.toastTimer = setTimeout(() => {
      this.showToast = false;
      this.addingStatus[id] = '';
    }, 2500);
  }

  toggleWishlist(product: Product) {
    this.wishlistService.toggleWishlist(product);
  }

  get cartCount() {
    return this.cartService.getTotalItemsCount();
  }

  getProductCartQuantity(productId: number | undefined): number {
    if (!productId) return 0;
    const cart = this.cartService.getCart();
    const item = cart.find(i => i.product.id === productId);
    return item ? item.quantity : 0;
  }
}