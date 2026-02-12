import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WalletService } from '../services/wallet.service';
import { WalletTransaction } from '../models/models';

@Component({
  selector: 'app-wallet-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './wallet-history.component.html',
  styleUrl: './wallet-history.component.css',
})
export class WalletHistoryComponent implements OnInit {
  transactions = signal<WalletTransaction[]>([]);

  constructor(private walletService: WalletService) {}

  ngOnInit(): void {
    this.loadHistory();
  }

  loadHistory(): void {
    this.walletService.getTransactionHistory().subscribe({
      next: (data) => {
        this.transactions.set(data);
      },
      error: (err) => {
        console.error('Error loading wallet history:', err);
      },
    });
  }
}
