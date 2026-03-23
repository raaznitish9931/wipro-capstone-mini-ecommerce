// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { ActivatedRoute } from '@angular/router';
// import { ProductService } from '../../services/product.service';
// import { CartService } from '../../services/cart.service';
// import { AuthService } from '../../services/auth.service';
// import { FormsModule } from '@angular/forms';

// @Component({
//   selector: 'app-product-catalog',
//   standalone: true,
//   imports: [CommonModule, FormsModule],
//   templateUrl: './product-catalog.component.html',
//   styleUrls: ['./product-catalog.component.css']
// })
// export class ProductCatalogComponent implements OnInit {

//   product: any;
//   quantity = 1;
//   message = '';

//   constructor(
//     private route: ActivatedRoute,
//     private productService: ProductService,
//     private cartService: CartService,
//     private authService: AuthService
//   ) {}

//   ngOnInit(): void {
//     const id = Number(this.route.snapshot.paramMap.get('id'));
//     this.productService.getProductById(id).subscribe({
//       next: (data) => this.product = data
//     });
//   }

//   addToCart(): void {
//     const payload = {
//       userId: this.authService.getUserId(),
//       productId: this.product.id,
//       quantity: this.quantity
//     };

//     this.cartService.addToCart(payload).subscribe({
//       next: () => {
//         this.message = 'Product added to cart';
//         this.refreshCartCount();
//       },
//       error: () => {
//         this.message = 'Failed to add product to cart';
//       }
//     });
//   }

//   refreshCartCount(): void {
//     this.cartService.getCartByUserId(this.authService.getUserId()).subscribe({
//       next: (items) => this.cartService.setCartCount(items.length)
//     });
//   }
// }

