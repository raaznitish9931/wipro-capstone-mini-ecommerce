import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cartItems: any[] = [];

  addToCart(product: any) {
    this.cartItems.push(product);
  }

  getCart() {
    return this.cartItems;
  }
}