import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { OrderService } from '../../services/order.service';
import { User } from '../../models/user';
import { Order } from '../../models/order';
import { NavbarComponent } from '../navbar/navbar.component';
import { ConfirmationModalComponent } from '../confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'app-my-orders',
  standalone: true,
  imports: [CommonModule, RouterModule, NavbarComponent, ConfirmationModalComponent],
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent implements OnInit {

  user: User | null = null;
  myOrders: Order[] = [];

  // Modal State
  isModalOpen: boolean = false;
  selectedOrderId: string | number | undefined = undefined;

  constructor(
    private authService: AuthService,
    private orderService: OrderService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getLoggedInUser();
    if (!this.user) {
      this.router.navigate(['/login']);
      return;
    }

    if (this.user?.id) {
      this.loadOrders();
    }
  }

  loadOrders(): void {
    if (this.user?.id) {
      const allOrders = this.orderService.getOrders();
      this.myOrders = allOrders.filter(o => o.userId === this.user?.id).reverse();
    }
  }

  openCancelModal(orderId: string | number | undefined): void {
    this.selectedOrderId = orderId;
    this.isModalOpen = true;
  }

  onModalConfirm(): void {
    if (this.selectedOrderId) {
      this.orderService.cancelOrder(this.selectedOrderId);
      this.loadOrders(); // Refresh the list
    }
    this.closeModal();
  }

  closeModal(): void {
    this.isModalOpen = false;
    this.selectedOrderId = undefined;
  }
}
