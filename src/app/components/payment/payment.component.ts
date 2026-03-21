import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { CartService } from '../../services/cart.service';
import { OrderService } from '../../services/order.service';
import { AuthService } from '../../services/auth.service';
import { Order } from '../../models/order';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.css'
})
export class PaymentComponent implements OnInit {
  paymentMethod: string = 'credit';
  totalAmount: number = 0;
  isProcessing: boolean = false;
  paymentSuccess: boolean = false;

  // Form Fields
  cardNumber: string = '';
  expiry: string = '';
  cvv: string = '';
  upiId: string = '';

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private authService: AuthService,
    private router: Router,
    private http: HttpClient
  ) { }

  get isFormValid(): boolean {
    if (this.paymentMethod === 'credit' || this.paymentMethod === 'debit') {
      // Very basic validation: Card number (16-19 chars with spaces), Expiry (5 chars), CVV (3 chars)
      return this.cardNumber.length >= 16 &&
        this.expiry.length === 5 &&
        this.cvv.length === 3;
    } else if (this.paymentMethod === 'upi') {
      return this.upiId.includes('@') && this.upiId.length > 3;
    }
    return false;
  }

  ngOnInit() {
    this.totalAmount = this.cartService.getTotalPrice();
    if (this.totalAmount === 0) {
      this.router.navigate(['/cart']);
    }
  }

  processPayment() {
    this.isProcessing = true;

    const user = this.authService.getLoggedInUser();
    const cartItems = this.cartService.getCart();
    const userId = user ? user.id : 0;

    if (!userId) {
       this.isProcessing = false;
       alert("User session lost. Please login again.");
       return;
    }

    const orderRequests = cartItems.map(item => {
      const backendReq = {
        userId: userId,
        productId: item.product.id,
        quantity: item.quantity
      };
      // Use observable to capture return IDs from backend
      return this.http.post<any>('http://localhost:9999/order', backendReq);
    });

    import('rxjs').then(({ forkJoin, of }) => {
      import('rxjs/operators').then(({ catchError }) => {
        forkJoin(orderRequests.map(req => req.pipe(catchError(err => {
          console.error('Backend sync failed for an item:', err);
          return of(null);
        }))))
        .subscribe((results: any[]) => {
          this.isProcessing = false;
          this.paymentSuccess = true;

          const backendIds = results.filter(r => r && r.id).map(r => r.id);

          // 2. Save structured UI Order object locally including backend mappings
          const newOrder: Order = {
            userId: userId || undefined,
            items: cartItems,
            totalAmount: this.totalAmount,
            paymentMethod: this.paymentMethod,
            orderDate: new Date(),
            status: 'Successful',
            backendIds: backendIds
          };

          this.orderService.placeOrder(newOrder);
          this.cartService.clearCart();
        });
      });
    });
  }
}
