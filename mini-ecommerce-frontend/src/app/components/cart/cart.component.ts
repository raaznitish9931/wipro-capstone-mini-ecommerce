import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CartService, CartItem } from '../../services/cart.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cartItems: CartItem[] = [];

  constructor(public cartService: CartService) {}

  ngOnInit() {
    this.cartItems = this.cartService.getCart();
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