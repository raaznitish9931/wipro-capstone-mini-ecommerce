import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Order } from '../models/order';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private STORAGE_KEY = 'userOrders';
  private backendUrl = 'http://localhost:9999/order';

  constructor(private http: HttpClient) { }

  placeOrder(order: Order): void {
    const orders = this.getOrders();
    order.orderId = 'ORD-' + Math.floor(100000 + Math.random() * 900000); // Random generated ID
    orders.push(order);
    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(orders));
  }

  getOrders(): Order[] {
    const ordersJson = localStorage.getItem(this.STORAGE_KEY);
    return ordersJson ? JSON.parse(ordersJson) : [];
  }

  cancelOrder(localOrderId: string | number): void {
    const orders = this.getOrders();
    const orderIndex = orders.findIndex(o => o.orderId === localOrderId);

    if (orderIndex !== -1) {
      const order = orders[orderIndex];
      
      // Update local storage status
      order.status = 'Cancelled';
      localStorage.setItem(this.STORAGE_KEY, JSON.stringify(orders));

      // Sync with backend to restore stock if backend IDs exist
      if (order.backendIds && order.backendIds.length > 0) {
        const cancelRequests = order.backendIds.map(id => 
          this.http.put(`${this.backendUrl}/${id}`, {}).pipe(
            catchError(err => {
              console.error(`Failed to cancel backend order ${id}:`, err);
              return of(null);
            })
          )
        );

        forkJoin(cancelRequests).subscribe(results => {
          console.log('All backend orders cancellation synced', results);
        });
      }
    }
  }
}