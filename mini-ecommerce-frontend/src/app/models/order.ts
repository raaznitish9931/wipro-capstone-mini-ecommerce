export interface Order {
  orderId?: string | number;
  backendIds?: number[]; // Added to track backend primary keys
  userId?: number;
  items: any[];
  totalAmount: number;
  paymentMethod: string;
  orderDate: Date | string;
  status: string;
}