import { Injectable } from '@angular/core';
import { Product } from '../models/product';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WishlistService {
  private STORAGE_KEY = 'userWishlist';
  private wishlistItems = new BehaviorSubject<Product[]>([]);
  wishlist$ = this.wishlistItems.asObservable();

  constructor() {
    this.loadWishlist();
  }

  private loadWishlist() {
    const stored = localStorage.getItem(this.STORAGE_KEY);
    if (stored) {
      this.wishlistItems.next(JSON.parse(stored));
    }
  }

  getWishlist(): Product[] {
    return this.wishlistItems.value;
  }

  toggleWishlist(product: Product): boolean {
    const current = this.wishlistItems.value;
    const index = current.findIndex(p => p.id === product.id);
    let added = false;

    if (index === -1) {
      current.push(product);
      added = true;
    } else {
      current.splice(index, 1);
      added = false;
    }

    this.wishlistItems.next([...current]);
    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(current));
    return added;
  }

  isInWishlist(productId: number | undefined): boolean {
    if (!productId) return false;
    return this.wishlistItems.value.some(p => p.id === productId);
  }

  clearWishlist() {
    this.wishlistItems.next([]);
    localStorage.removeItem(this.STORAGE_KEY);
  }
}
