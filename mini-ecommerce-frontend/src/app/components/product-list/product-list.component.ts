


import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../services/product.service';
import { AuthService } from '../../services/auth.service';
import { Product } from '../../models/product';
import { User } from '../../models/user';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, NavbarComponent],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];
  filteredProducts: Product[] = [];
categories: string[] = [
  'Electronics',
  'Fashion',
  'Beauty & Personal Care',
  'Home & Furniture',
  'Stationery',
  'Grocery'
];

  loading = true;
  message = '';

  searchText = '';
  selectedCategory = '';
  selectedPrice = '';

  role: string = '';

  showConfirm = false;
  selectedProductId: number | null = null;
  user: User | null = null;
  isDropdownOpen = false;

  constructor(
    private productService: ProductService,
    private authService: AuthService,
    private router: Router,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getLoggedInUser();
    this.role = this.user?.role || '';
    this.loadProducts();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  get userRole(): string {
    if (!this.user || !this.user.role) return 'User';
    return this.user.role.toUpperCase() === 'ADMIN' ? 'Admin' : 'User';
  }

  toggleDropdown(): void {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  goBack(): void {
    this.location.back();
  }

  loadProducts(): void {
    this.productService.getAllProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.filteredProducts = data;
        this.loading = false;
      },
      error: (err) => {
        this.loading = false;
        this.message = err.error?.message || 'Service is currently unavailable. Please try again later.';
      },
    });
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

  editProduct(id?: number): void {
    if (id !== undefined && id !== null) {
      this.router.navigate(['/edit-product', id]);
    } else {
      console.warn('Product ID is missing');
    }
  }

  confirmDelete(id?: number) {
    if (id === undefined || id === null) return;
    this.selectedProductId = id;
    this.showConfirm = true;
  }

  deleteConfirmed() {
    if (this.selectedProductId === undefined || this.selectedProductId === null) return;

    this.productService.deleteProduct(this.selectedProductId).subscribe({
      next: () => {
        this.message = 'Product deleted successfully';
        this.loadProducts();
        this.showConfirm = false;
      },
      error: (err) => {
        console.error('Delete failed', err);
        this.message = 'Failed to delete product. Please try again.';
        this.showConfirm = false;
      }
    });
  }

  cancelDelete() {
    this.showConfirm = false;
  }
}