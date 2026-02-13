import { Component, OnInit, signal, computed } from '@angular/core';
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
  currentPage = signal(1);
  pageSize = signal(10);

  paginatedTransactions = computed(() => {
    const startIndex = (this.currentPage() - 1) * this.pageSize();
    return this.transactions().slice(startIndex, startIndex + this.pageSize());
  });

  totalPages = computed(() => Math.ceil(this.transactions().length / this.pageSize()));

  nextPage() {
    if (this.currentPage() < this.totalPages()) {
      this.currentPage.update((p) => p + 1);
    }
  }

  prevPage() {
    if (this.currentPage() > 1) {
      this.currentPage.update((p) => p - 1);
    }
  }

  constructor(private walletService: WalletService) { }

  ngOnInit(): void {
    this.loadHistory();
  }

  loadHistory(): void {
    this.walletService.getTransactionHistory().subscribe({
      next: (data) => {
        this.transactions.set(data);
        this.currentPage.set(1);
      },
      error: (err) => {
        console.error('Error loading wallet history:', err);
      },
    });
  }
}
