import { Injectable } from '@angular/core';

export interface CartItem {
  product: any;
  quantity: number;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartItems: CartItem[] = [];

  addToCart(product: any) {
    const existing = this.cartItems.find(item => item.product.id === product.id);
    if (existing) {
      existing.quantity += 1;
    } else {
       this.cartItems.push({ product, quantity: 1 });
    }
  }

  getCart(): CartItem[] {
    return this.cartItems;
  }

  removeFromCart(productId: number) {
    this.cartItems = this.cartItems.filter(item => item.product.id !== productId);
  }

  updateQuantity(productId: number, quantity: number) {
    const item = this.cartItems.find(i => i.product.id === productId);
    if (item) {
      item.quantity = quantity;
      if (item.quantity <= 0) {
        this.removeFromCart(productId);
      }
    }
  }

  getTotalPrice(): number {
    return this.cartItems.reduce((acc, item) => acc + (item.product.price * item.quantity), 0);
  }

  clearCart() {
    this.cartItems = [];
  }

  getTotalItemsCount(): number {
    return this.cartItems.reduce((acc, item) => acc + item.quantity, 0);
  }
}