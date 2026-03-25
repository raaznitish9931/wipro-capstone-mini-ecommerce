import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CartService, CartItem } from '../../services/cart.service';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, RouterModule, NavbarComponent],
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cartItems: CartItem[] = [];
  serviceDown = false;

  constructor(
    public cartService: CartService,
    private productService: ProductService
  ) {}

  ngOnInit() {
    this.cartItems = this.cartService.getCart();
    this.refreshStock();
  }

  refreshStock() {
    this.productService.getAllProducts().subscribe({
      next: (products) => {
        this.serviceDown = false;
        this.syncStock(products);
      },
      error: () => {
        this.serviceDown = true;
      }
    });
  }

  private syncStock(liveProducts: Product[]) {
    this.cartItems.forEach(item => {
      const live = liveProducts.find(p => p.id === item.product.id);
      if (live) {
        item.product.quantity = live.quantity;
      }
    });
  }

  get invalidItems(): CartItem[] {
    return this.cartItems.filter(item => item.quantity > item.product.quantity);
  }

  incrementQuantity(productId: number, currentQty: number) {
     this.cartService.updateQuantity(productId, currentQty + 1);
     this.cartItems = this.cartService.getCart();
  }

  decrementQuantity(productId: number, currentQty: number) {
     this.cartService.updateQuantity(productId, currentQty - 1);
     this.cartItems = this.cartService.getCart();
  }

  removeItem(productId: number) {
     this.cartService.removeFromCart(productId);
     this.cartItems = this.cartService.getCart();
  }
}