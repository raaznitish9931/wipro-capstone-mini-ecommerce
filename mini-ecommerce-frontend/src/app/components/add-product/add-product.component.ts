


import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { AuthService } from '../../services/auth.service';
import { Product } from '../../models/product';
import { User } from '../../models/user';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-add-product',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, NavbarComponent],
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  product: Product = {
    name: '',
    description: '',
    price: 0,
    quantity: 0,
    category: '',
    brand: '',
    imageUrl: ''
  };

  message = '';
  isEditMode = false;
  productId!: number;
  user: User | null = null;
  role: string = '';
  isDropdownOpen = false;

  categories: string[] = [
    'Electronics',
    'Fashion',
    'Beauty & Personal Care',
    'Home & Furniture',
    'Stationery',
    'Grocery'
  ];

  constructor(
    private productService: ProductService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getLoggedInUser();
    this.role = this.user?.role || '';
    
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.productId = Number(id);
      this.productService.getProductById(this.productId).subscribe({
        next: (data) => {
          this.product = data;
        },
        error: () => {
          this.message = 'Unable to fetch product details';
        }
      });
    }
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

  saveProduct(): void {
    if (this.isEditMode) {
      this.productService.updateProduct(this.productId, this.product).subscribe({
        next: () => {
          this.message = 'Product updated successfully';
          setTimeout(() => this.router.navigate(['/product-list']), 1000);
        },
        error: () => {
          this.message = 'Failed to update product';
        }
      });
    } else {
      this.productService.addProduct(this.product).subscribe({
        next: () => {
          this.message = 'Product added successfully';
          setTimeout(() => this.router.navigate(['/product-list']), 1000);
        },
        error: () => {
          this.message = 'Failed to add product';
        }
      });
    }
  }
}