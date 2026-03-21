


import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
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
    private router: Router
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

  loadProducts(): void {
    this.productService.getAllProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.filteredProducts = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.message = 'Unable to load products';
      }
    });
  }

  applyFilters() {
    this.filteredProducts = this.products.filter(product => {

      const matchSearch =
        !this.searchText ||
        product.name?.toLowerCase().includes(this.searchText.toLowerCase());

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
    if (id) {
      this.router.navigate(['/edit-product', id]);
    }
  }

  confirmDelete(id?: number) {
    if (!id) return;
    this.selectedProductId = id;
    this.showConfirm = true;
  }

  deleteConfirmed() {
    if (!this.selectedProductId) return;

    this.productService.deleteProduct(this.selectedProductId).subscribe(() => {
      this.message = 'Product deleted successfully';
      this.loadProducts();
      this.showConfirm = false;
    });
  }

  cancelDelete() {
    this.showConfirm = false;
  }
}